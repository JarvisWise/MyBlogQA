package org.simple.blog.controllers;

import org.apache.log4j.Logger;
import org.simple.blog.dao.interfaces.blog.DAOComment;
import org.simple.blog.dao.interfaces.blog.DAOPost;
import org.simple.blog.dao.interfaces.blog.DAOUser;
import org.simple.blog.entities.blog.BlogCommentSet;
import org.simple.blog.entities.blog.BlogPost;
import org.simple.blog.entities.blog.BlogPostSet;
import org.simple.blog.entities.blog.BlogUser;
import org.simple.blog.tools.custom.exceptions.WrongEntityIdException;
import org.simple.blog.tools.strings.AttributeName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

import static org.simple.blog.tools.strings.PageName.*;

@Controller
@RequestMapping(value = "/show")
public class ShowController extends AbstractController{

    private final DAOPost daoPost;
    private final DAOComment daoComment;
    private final DAOUser daoUser;
    private static final Logger logger = Logger.getLogger(ShowController.class);


    @Autowired
    public ShowController(DAOPost daoPost, DAOComment daoComment, DAOUser daoUser) {
        this.daoPost = daoPost;
        this.daoComment = daoComment;
        this.daoUser = daoUser;
    }

    @RequestMapping(value = "/post", method= RequestMethod.GET)
    public ModelAndView showByPostId(@RequestParam("postId") String postId,
                                     HttpServletRequest request) {

        if (!isLogInUserAction(request))
            return redirectToLogin(request);

        ModelAndView modelAndView = new ModelAndView();
        BlogPost blogPost = null;
        BlogUser blogPostUser = null;
        List<BlogCommentSet> commentSets = null;

        try {
            blogPost = daoPost.getPostById(postId);
            blogPostUser = daoUser.getUserById(blogPost.getPostUserId());
            commentSets = daoComment.getCommentSetByPostId(postId);
            modelAndView.addObject("blogPost", blogPost);
            modelAndView.addObject("blogPostUser", blogPostUser);
            modelAndView.addObject("commentSets", commentSets);
            modelAndView.setViewName(SHOW_POST_PAGE.getPageName());
        } catch (WrongEntityIdException e) {
            logger.warn(e);
            modelAndView.setViewName(ERROR_PAGE.getPageName());
        }

        return modelAndView;
    }

    @RequestMapping(value = "/blog-profile", method= RequestMethod.GET)
    public ModelAndView showByUserId(@RequestParam("userId") String userId,
                                     HttpServletRequest request) {

        if (!isLogInUserAction(request))
            return redirectToLogin(request);

        ModelAndView modelAndView = new ModelAndView();
        String profileUserId = null;
        BlogUser blogUser = null;
        HashMap<BlogPost, String> postsWithC = null;

        if (userId == null || "".equals(userId)) {
            profileUserId = userId;
        } else {
            HttpSession session = request.getSession(false);
            profileUserId = (String) session.getAttribute(AttributeName.CURRENT_USER_ID.getAttributeName());
        }

        try {
            blogUser = daoUser.getUserById(profileUserId);
            postsWithC = daoPost.getPostWithCommentCount(profileUserId);
            modelAndView.addObject("blogUser", blogUser);
            modelAndView.addObject("postsWithC", postsWithC);
            modelAndView.setViewName(BLOG_PROFILE_PAGE.getPageName());
        } catch (WrongEntityIdException e) {
            logger.warn(e);
            modelAndView.setViewName(ERROR_PAGE.getPageName());
        }


        return modelAndView;
    }

    @RequestMapping(value = "/post-list", method= RequestMethod.GET)
    public ModelAndView showPostsList(@RequestParam(required = false) String page,
                                      HttpServletRequest request) {

        if (!isLogInUserAction(request))
            return redirectToLogin(request);

        ModelAndView modelAndView = new ModelAndView();
        int currentPage;
        Integer countOfPages = daoPost.getPageCount();
        List<BlogPostSet> postSets = null;
        if (page == null || "".equals(page))  {
            currentPage = 1;
        } else {
            currentPage = Integer.parseInt(page);
            if (currentPage < 1 || currentPage > countOfPages)
                currentPage = 1;
        }
        try {
            postSets = daoPost.getPostSetByPage(currentPage);
            modelAndView.addObject("postSets", postSets);
            modelAndView.addObject("currentPage", currentPage);
            modelAndView.addObject("countOfPages", countOfPages);
            modelAndView.setViewName(SHOW_POSTS_PAGE.getPageName());
        } catch (WrongEntityIdException e) {
            logger.warn(e);
            modelAndView.setViewName(ERROR_PAGE.getPageName());
        }

        return modelAndView;
    }
}
