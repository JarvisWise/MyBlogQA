package org.example.controllers;

import org.example.dao.implementations.DAOStudentImpl;
import org.example.dao.implementations.DAOTeacherImpl;
import org.example.entities.Student;
import org.example.entities.Teacher;
import org.example.tools.custom.exceptions.WrongLoginDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;

import static org.example.tools.strings.PageName.*;

@Controller
public class LoginController extends AbstractController{

    private final DAOStudentImpl daoStudent;
    private final DAOTeacherImpl daoTeacher;

    @Autowired
    public LoginController(DAOStudentImpl daoStudent, DAOTeacherImpl daoTeacher) {
        this.daoStudent = daoStudent;
        this.daoTeacher = daoTeacher;
    }

    @RequestMapping(value = "/login")
    @GetMapping
    public ModelAndView checkUser(@RequestParam("loginUserName") String email,
                                   @RequestParam("loginPassword") String password,
                                   HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            if (daoTeacher.isExistTeacherByEmail(email)) {
                modelAndView = loginTeacher(email, password, request);
            } else if (daoStudent.isExistStudentByEmail(email)) {
                modelAndView = loginStudent(email, password, request);
            } else {
                modelAndView.addObject("ExceptionMessage", "You entered wrong login!");
                modelAndView.setViewName(LOGIN_PAGE.getPageName());
            }
        } catch (SQLException e) {
            modelAndView.addObject("ExceptionMessage", "Sorry, we have unexpected error!");
            modelAndView.setViewName(LOGIN_PAGE.getPageName());
        }
        return modelAndView;
    }

    private ModelAndView loginTeacher(String email, String password, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Teacher teacher = daoTeacher.getTeacherByEmailAndPassword(email, password);
            setBaseSessionVariables(request, teacher);
            modelAndView.setViewName("redirect:/show/teacher?teacherId=" + teacher.getTeacherId());
        } catch (WrongLoginDataException e) {
            modelAndView.addObject("ExceptionMessage", "You entered wrong password!");
            modelAndView.setViewName(LOGIN_PAGE.getPageName());
        }
        return modelAndView;
    }

    private ModelAndView loginStudent(String email, String password, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Student student = daoStudent.getStudentByEmailAndPassword(email, password);
            setBaseSessionVariables(request, student);
            modelAndView.setViewName("redirect:/show/student?studentId=" + student.getStudentId());
        } catch (WrongLoginDataException e) {
            modelAndView.addObject("ExceptionMessage", "You entered wrong password!");
            modelAndView.setViewName(LOGIN_PAGE.getPageName());
        }
        return modelAndView;
    }
}
