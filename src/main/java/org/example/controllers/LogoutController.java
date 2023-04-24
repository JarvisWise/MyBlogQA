package org.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.net.HttpCookie;

import static org.example.tools.strings.PageName.LOGIN_PAGE;

@Controller
public class LogoutController {

    @RequestMapping(value = "/logout")
    @GetMapping
    public ModelAndView logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate(); //destroy session
        //HttpCookie also try cookie next
        return new ModelAndView("redirect:/redirect/login");
    }
}
