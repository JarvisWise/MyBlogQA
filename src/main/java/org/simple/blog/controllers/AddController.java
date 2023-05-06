package org.simple.blog.controllers;

import org.apache.log4j.Logger;
import org.simple.blog.dao.interfaces.blog.DAOComment;
import org.simple.blog.dao.interfaces.blog.DAOPost;
import org.simple.blog.dao.interfaces.blog.DAOUser;
import org.simple.blog.entities.blog.BlogComment;
import org.simple.blog.entities.blog.BlogPost;
import org.simple.blog.tools.strings.AttributeName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.simple.blog.tools.BlogConstants.*;
import static org.simple.blog.tools.strings.PageName.*;

@Controller
@RequestMapping(value = "/add")
public class AddController extends AbstractController{

    private final DAOPost daoPost;
    private final DAOComment daoComment;
    private final DAOUser daoUser;
    private static final Logger logger = Logger.getLogger(AddController.class);


    @Autowired
    public AddController(DAOPost daoPost, DAOComment daoComment, DAOUser daoUser) {

        this.daoPost = daoPost;
        this.daoComment = daoComment;
        this.daoUser = daoUser;
    }

    @RequestMapping(value = "/post")
    @GetMapping
    public ModelAndView addNewPost(@RequestParam("postName") String postName,
                                    @RequestParam("postText") String postText,
                                   HttpServletRequest request) {

        if (!isLogInUserAction(request))
            return redirectToLogin(request);

        ModelAndView modelAndView = new ModelAndView();
        LocalDateTime now = LocalDateTime.now();
        HttpSession session = request.getSession(false);
        String currentUserId = (String) session.getAttribute(AttributeName.CURRENT_USER_ID.getAttributeName());

        try {
            daoPost.addPost(new BlogPost(
                null, postText, postName,
                DATE_TIME_FORMATTER.format(now),
                currentUserId, null
            ));
            modelAndView.setViewName("redirect:/show/post-list");
        } catch (SQLException e) {
            logger.warn(e);
            modelAndView.setViewName(ERROR_PAGE.getPageName());
        }

        return modelAndView;
    }

    @RequestMapping(value = "/comment")
    @GetMapping
    public ModelAndView addNewComment(@RequestParam("commentText") String commentText,
                                   @RequestParam("postId") String postId,
                                   @RequestParam("parentId") String parentId,
                                   HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();
        LocalDateTime now = LocalDateTime.now();
        HttpSession session = request.getSession(false);
        String currentUserId = (String) session.getAttribute(AttributeName.CURRENT_USER_ID.getAttributeName());

        try {
            daoComment.addComment(new BlogComment(
                    null, commentText, DATE_TIME_FORMATTER.format(now),
                    currentUserId, postId, "".equals(parentId) ? null : parentId, null
            ));
            modelAndView.setViewName("redirect:/show/post?postId="+postId);
        } catch (SQLException e) {
            logger.warn(e);
            modelAndView.setViewName(ERROR_PAGE.getPageName());
        }

        return modelAndView;
    }
}
