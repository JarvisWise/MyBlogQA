package org.simple.blog.controllers;

import org.apache.log4j.Logger;
import org.simple.blog.dao.interfaces.blog.DAOUser;
import org.simple.blog.entities.blog.BlogUser;
import org.simple.blog.tools.custom.exceptions.WrongLoginDataException;
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

import java.sql.SQLException;

import static org.simple.blog.tools.strings.PageName.*;

@Controller
public class LoginController extends AbstractController{

    private final DAOUser daoUser;
    private static final Logger logger = Logger.getLogger(LoginController.class);

    @Autowired
    public LoginController(DAOUser daoUser) {
        this.daoUser = daoUser;
    }

    @RequestMapping(value = "/", method= RequestMethod.GET)
    public ModelAndView start() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(LOGIN_PAGE.getPageName());
        return modelAndView;
    }

    @RequestMapping(value = "/logout", method= RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();
        return new ModelAndView("redirect:/redirect/login");
    }

    @RequestMapping(value = "/login", method= RequestMethod.POST)
    public ModelAndView checkBlogUser(@RequestParam("loginUserName") String email,
                                  @RequestParam("loginPassword") String password,
                                  HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            if (daoUser.isExistUserByEmail(email)) {
                BlogUser blogUser = daoUser.getUserByEmailAndPassword(email, password);
                setBaseBlogSessionVariables(request, blogUser);
                modelAndView.setViewName("redirect:/show/post-list");
            } else {
                modelAndView.addObject(AttributeName.EXCEPTION_MESSAGE.getAttributeName(), "You entered wrong login!");
                modelAndView.setViewName(LOGIN_PAGE.getPageName());
            }
        } catch (SQLException e) {
            logger.warn(e);
            modelAndView.addObject(AttributeName.EXCEPTION_MESSAGE.getAttributeName(), "Sorry, we have unexpected error!");
            modelAndView.setViewName(LOGIN_PAGE.getPageName());
        } catch (WrongLoginDataException e) {
            logger.warn(e);
            modelAndView.addObject(AttributeName.EXCEPTION_MESSAGE.getAttributeName(), "You entered wrong password!");
            modelAndView.setViewName(LOGIN_PAGE.getPageName());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method= RequestMethod.POST)
    public ModelAndView registrationBlogUser(@RequestParam("email") String email,
                                      @RequestParam("firstName") String firstName,
                                      @RequestParam("lastName") String lastName,
                                      @RequestParam("birthday") String birthday,
                                      @RequestParam("password") String password,
                                      @RequestParam("cPassword") String cPassword) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            if (daoUser.isExistUserByEmail(email)) {
                modelAndView.addObject(AttributeName.EXCEPTION_MESSAGE.getAttributeName(), "This email already registered!");
                modelAndView.setViewName(REGISTRATION_PAGE.getPageName());
            } else if (!password.equals(cPassword)) {
                modelAndView.addObject(AttributeName.EXCEPTION_MESSAGE.getAttributeName(), "Password and Confirm Password are different!");
                modelAndView.setViewName(REGISTRATION_PAGE.getPageName());
            } else {

                daoUser.addUser(new BlogUser(
                        null, email,
                        firstName, lastName,
                        password, birthday
                ));
                modelAndView.setViewName(LOGIN_PAGE.getPageName());
            }
        } catch (SQLException e) {
            logger.warn(e);
            modelAndView.addObject(AttributeName.EXCEPTION_MESSAGE.getAttributeName(), "Sorry, we have unexpected error!");
            modelAndView.setViewName(REGISTRATION_PAGE.getPageName());
        }
        return modelAndView;
    }
}
