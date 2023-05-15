package org.simple.blog.controllers;

import org.apache.log4j.Logger;
import org.simple.blog.dao.interfaces.blog.DAOComment;
import org.simple.blog.dao.interfaces.blog.DAOPost;
import org.simple.blog.dao.interfaces.blog.DAOUser;
import org.simple.blog.entities.blog.BlogComment;
import org.simple.blog.entities.blog.BlogPost;
import org.simple.blog.entities.blog.BlogUser;
import org.simple.blog.tools.MailUtils;
import org.simple.blog.tools.custom.exceptions.WrongEntityIdException;
import org.simple.blog.tools.strings.AttributeName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.simple.blog.tools.BlogConstants.DATE_TIME_FORMATTER;
import static org.simple.blog.tools.strings.PageName.*;

@Controller
@RequestMapping(value = "/edit")
public class EditController extends AbstractController{

    private final DAOPost daoPost;
    private final DAOComment daoComment;
    private final DAOUser daoUser;
    private static final Logger logger = Logger.getLogger(EditController.class);
    private final static String LOGIN_USER_EMAIL = "test.mail@gmail.com";
    private final static String REG_USER_EMAIL = "reg.mail@gmail.com";


    @Autowired
    public EditController(DAOPost daoPost, DAOComment daoComment, DAOUser daoUser) {

        this.daoPost = daoPost;
        this.daoComment = daoComment;
        this.daoUser = daoUser;
    }

    //not implemented GUI
    @RequestMapping(value = "/post/{postId}", method= RequestMethod.POST)
    public ModelAndView editPost(@RequestParam("postName") String postName,
                                 @RequestParam("postText") String postText,
                                  @PathVariable String postId) {

        ModelAndView modelAndView = new ModelAndView();
        LocalDateTime now = LocalDateTime.now();
        try {
            daoPost.updatePost(new BlogPost(
                postId, postText, postName,
                DATE_TIME_FORMATTER.format(now),
                null, null
            ));
            modelAndView.setViewName("redirect:/show/post?postId="/*+groupId*/);//-to do
        } catch (SQLException e) {
            logger.warn(e);
            modelAndView.setViewName(ERROR_PAGE.getPageName());
        }
        return modelAndView;
    }

    //not implemented GUI
    @RequestMapping(value = "/comment/{commentId}", method= RequestMethod.POST)
    public ModelAndView editPost(@RequestParam("commentText") String commentText,
                                 @PathVariable String commentId) {

        ModelAndView modelAndView = new ModelAndView();
        LocalDateTime now = LocalDateTime.now();
        try {
            daoComment.updateComment(new BlogComment(
                    commentId, commentText, DATE_TIME_FORMATTER.format(now),
                    null, null, null, null
            ));
            modelAndView.setViewName("redirect:/show/group?groupId="/*+groupId*/);//-to do
        } catch (SQLException e) {
            logger.warn(e);
            modelAndView.setViewName(ERROR_PAGE.getPageName());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/forgot-password", method= RequestMethod.POST)
    public ModelAndView editUserResetToken(@RequestParam("email") String email) {
        ModelAndView modelAndView = new ModelAndView();

        try {
            if (daoUser.isExistUserByEmail(email)) {
                BlogUser user = daoUser.getUserByEmail(email);
                daoUser.setResetToken(user.getUserId(), UUID.randomUUID().toString());
                user = daoUser.getUserById(user.getUserId());
                MailUtils.sendEmail(user);
                modelAndView.addObject(AttributeName.SUCCESS_MESSAGE.getAttributeName(), "Check your mail box!");
            } else {
                modelAndView.addObject(AttributeName.EXCEPTION_MESSAGE.getAttributeName(), "Wrong Email!");
            }
            modelAndView.setViewName(BLOG_FORGOT_PASSWORD_PAGE.getPageName());
        } catch (SQLException | WrongEntityIdException | MessagingException | NamingException e) {
            logger.warn(e);
            modelAndView.setViewName(ERROR_PAGE.getPageName());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/reset-password/{token}", method= RequestMethod.POST)
    public ModelAndView resetUserPassword(@RequestParam("newPassword") String newPassword,
                                       @RequestParam("cPassword") String cPassword,
                                       @PathVariable String token) {

        ModelAndView modelAndView = new ModelAndView();

        if (newPassword.isEmpty() || cPassword.isEmpty()
                || !newPassword.equals(cPassword)) {
            modelAndView.addObject("userToken", token);
            modelAndView.addObject(AttributeName.EXCEPTION_MESSAGE.getAttributeName(), "Please enter valid password!");
            modelAndView.setViewName(BLOG_RESET_PASSWORD_PAGE.getPageName());
        } else {
            try {
                BlogUser user = daoUser.getUserByToken(token);
                daoUser.setNewPassword(user.getUserId(), newPassword);
                daoUser.setResetToken(user.getUserId(), null);
                modelAndView.addObject(AttributeName.SUCCESS_MESSAGE.getAttributeName(), "Password changed successfully!");
                modelAndView.setViewName(LOGIN_PAGE.getPageName());
            } catch (WrongEntityIdException e) {
                logger.warn(e);
                modelAndView.addObject(AttributeName.EXCEPTION_MESSAGE.getAttributeName(), "Wrong token!");
                modelAndView.setViewName(LOGIN_PAGE.getPageName());
            } catch (SQLException e) {
                logger.warn(e);
                modelAndView.setViewName(ERROR_PAGE.getPageName());
            }
        }

        return modelAndView;
    }

    @RequestMapping(value = "/userCommon/{userId}", method= RequestMethod.POST)
    public ModelAndView editUserCommon(@RequestParam("firstName") String firstName,
                                       @RequestParam("lastName") String lastName,
                                       @RequestParam("birthday") String birthday,
                                       @PathVariable String userId,
                                       HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            daoUser.updateUserCommon(userId, firstName, lastName, birthday);
            BlogUser blogUser = daoUser.getUserById(userId);
            setBaseBlogSessionVariables(request, blogUser);
            modelAndView.setViewName("redirect:/show/blog-profile?userId=" + blogUser.getUserId());
        } catch (SQLException | WrongEntityIdException e) {
            logger.warn(e);
            modelAndView.setViewName(ERROR_PAGE.getPageName());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/clean-test-user", method= RequestMethod.GET)
    public ModelAndView editUserCommon(HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            BlogUser testUser = daoUser.getUserByEmail(LOGIN_USER_EMAIL);
            daoComment.deleteCommentsByUserId(testUser.getUserId());
            daoPost.deletePostsByUserId(testUser.getUserId());
            daoUser.deleteUserByEmail(REG_USER_EMAIL);
        } catch (WrongEntityIdException e) {
            logger.warn(e);

        }
        modelAndView.setViewName(LOGIN_PAGE.getPageName());
        return modelAndView;
    }

}
