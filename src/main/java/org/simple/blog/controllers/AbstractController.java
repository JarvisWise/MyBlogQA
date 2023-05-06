package org.simple.blog.controllers;

import org.simple.blog.entities.blog.BlogUser;
import org.simple.blog.tools.strings.AttributeName;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import static org.simple.blog.tools.strings.PageName.*;

public abstract class AbstractController {

    protected void setBaseBlogSessionVariables(HttpServletRequest request, BlogUser user) {
        HttpSession session = request.getSession(false);
        session.setAttribute(
                AttributeName.CURRENT_USER_ID.getAttributeName(),
                user.getUserId()
        );
        session.setAttribute(
                AttributeName.CURRENT_USERNAME.getAttributeName(),
                user.getFullName()
        );
    }

    protected boolean isLogInUserAction(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String currentUserId = (String) session.getAttribute(AttributeName.CURRENT_USER_ID.getAttributeName());
        String currentUserName = (String) session.getAttribute(AttributeName.CURRENT_USERNAME.getAttributeName());

        return currentUserId != null && currentUserName != null
                && !currentUserId.isEmpty() && !currentUserName.isEmpty();
    }

    protected ModelAndView redirectToLogin(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession(false);
        session.invalidate();

        modelAndView.addObject(AttributeName.EXCEPTION_MESSAGE.getAttributeName(), "You are not logged yet!");
        modelAndView.setViewName(LOGIN_PAGE.getPageName());
        return modelAndView;
    }
}
