package org.simple.blog.controllers;

import org.apache.log4j.Logger;
import org.simple.blog.dao.interfaces.blog.DAOComment;
import org.simple.blog.dao.interfaces.blog.DAOPost;
import org.simple.blog.dao.interfaces.blog.DAOUser;
import org.simple.blog.tools.custom.exceptions.WrongEntityIdException;
import org.simple.blog.tools.strings.PageName;
import org.simple.blog.tools.strings.AttributeName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/redirect")
public class RedirectController extends AbstractController{

    private final DAOPost daoPost;
    private final DAOComment daoComment;
    private final DAOUser daoUser;
    private static final Logger logger = Logger.getLogger(RedirectController.class);


    @Autowired
    public RedirectController(DAOPost daoPost, DAOComment daoComment, DAOUser daoUser) {
        this.daoPost = daoPost;
        this.daoComment = daoComment;
        this.daoUser = daoUser;
    }

    @RequestMapping(value = "/add/post")
    @GetMapping
    public ModelAndView redirectAddPost(HttpServletRequest request) {

        if (!isLogInUserAction(request))
            return redirectToLogin(request);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.ADD_POST_PAGE.getPageName());
        return modelAndView;
    }

    @RequestMapping(value = "/reset-password")
    @GetMapping
    public ModelAndView redirectResetPassword(@RequestParam("token") String token) {
        ModelAndView modelAndView = new ModelAndView();

        try {
            if (token != null && !"".equals(token)) {
                daoUser.getUserByToken(token);
                modelAndView.addObject("userToken", token);
                modelAndView.setViewName(PageName.BLOG_RESET_PASSWORD_PAGE.getPageName());
            } else {
                throw new WrongEntityIdException("Empty token");
            }
        } catch (WrongEntityIdException e) {
            logger.warn(e);
            modelAndView.addObject(AttributeName.EXCEPTION_MESSAGE.getAttributeName(), "Invalid Token, try again!");
            modelAndView.setViewName(PageName.BLOG_RESET_PASSWORD_PAGE.getPageName());
        }

        return modelAndView;
    }

    @RequestMapping(value = "/forgot-password")
    @GetMapping
    public ModelAndView redirectForgotPassword() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.BLOG_FORGOT_PASSWORD_PAGE.getPageName());
        return modelAndView;
    }

    @RequestMapping(value = "/login")
    @GetMapping
    public ModelAndView redirectLogin() {
        return new ModelAndView(PageName.LOGIN_PAGE.getPageName());
    }

    @RequestMapping(value = "/registration")
    @GetMapping
    public ModelAndView redirectRegistration() {
        return new ModelAndView(PageName.REGISTRATION_PAGE.getPageName());
    }
}
