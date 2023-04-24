package org.example.controllers;

import org.example.dao.implementations.*;
import org.example.entities.*;
import org.example.tools.custom.exceptions.WrongEntityIdException;
import org.example.tools.strings.PageName;
import org.example.tools.strings.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

import static org.example.tools.strings.PageName.ERROR_PAGE;
import static org.example.tools.strings.Role.STUDENT;
import static org.example.tools.strings.Role.TEACHER;
import static org.example.tools.strings.SessionAttributeName.*;

@Controller
@RequestMapping(value = "/edit")
public class EditController {

    private final DAOStudentImpl daoStudent;
    private final DAOTeacherImpl daoTeacher;
    private final DAOGroupImpl daoGroup;
    private final DAOSubjectImpl daoSubject;
    private final DAOStudentSubjectImpl daoStudentSubject;
    private final DAOTaskImpl daoTask;
    private final DAOStudentTaskImpl daoStudentTask;

    @Autowired
    public EditController(DAOStudentImpl daoStudent, DAOTeacherImpl daoTeacher, DAOGroupImpl daoGroup, DAOSubjectImpl daoSubject, DAOStudentSubjectImpl daoStudentSubject, DAOTaskImpl daoTask, DAOStudentTaskImpl daoStudentTask) {
        this.daoStudent = daoStudent;
        this.daoTeacher = daoTeacher;
        this.daoGroup = daoGroup;
        this.daoSubject = daoSubject;
        this.daoStudentSubject = daoStudentSubject;
        this.daoTask = daoTask;
        this.daoStudentTask = daoStudentTask;
    }

    @RequestMapping(value = "/group/{groupId}")
    @GetMapping
    public ModelAndView editGroup(@RequestParam("groupName") String groupName,
                                      @RequestParam("groupDescription") String groupDescription,
                                      @PathVariable String groupId) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            daoGroup.updateGroup(new Group(groupId, groupName, groupDescription));
            modelAndView.setViewName("redirect:/show/group?groupId="+groupId);//
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            modelAndView.setViewName(ERROR_PAGE.getPageName());
        }
        //add action
        return modelAndView;
    }

    @RequestMapping(value = "/student/{studentId}")
    @GetMapping
    public ModelAndView editStudent(@RequestParam("loginName") String email,
                                        @RequestParam("firstName") String firstName,
                                        @RequestParam("lastName") String lastName,
                                        @RequestParam("headman") String headman,
                                        @RequestParam("password") String password,
                                        @RequestParam("groupId") String groupId,
                                        @PathVariable String studentId) {

        ModelAndView modelAndView = new ModelAndView();
        Student student = new Student(
                studentId,
                email,
                firstName,
                lastName,
                password,
                Strings.NOT_YET.getStrings().equals(headman) ? null : headman ,
                Strings.NOT_YET.getStrings().equals(groupId) ? null : groupId
        );

        try {
            daoStudent.updateStudent(student);
            modelAndView.setViewName("redirect:/redirect/profile?userId="+studentId+"&userRole="+student.getRole());//
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            modelAndView.setViewName(ERROR_PAGE.getPageName());
        }
        //add action
        return modelAndView;
    }

    @RequestMapping(value = "/teacher/{teacherId}")
    @GetMapping
    public ModelAndView editTeacher(@RequestParam("loginName") String email,
                                        @RequestParam("firstName") String firstName,
                                        @RequestParam("lastName") String lastName,
                                        @PathVariable String teacherId) {

        ModelAndView modelAndView = new ModelAndView();
        Teacher teacher = new Teacher(
                teacherId,
                email,
                firstName,
                lastName,
                null
        );

        try {
            daoTeacher.updateTeacher(teacher);
            modelAndView.setViewName("redirect:/show/teacher?teacherId="+teacherId);//
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            modelAndView.setViewName(ERROR_PAGE.getPageName());
        }
        //add action
        return modelAndView;
    }

    @RequestMapping(value = "/student-group")
    @GetMapping
    public ModelAndView editStudentGroup(@RequestParam("studentId") String studentId,
                                        @RequestParam("groupId") String groupId) {

        ModelAndView modelAndView = new ModelAndView();

        try {
            daoStudent.updateGroupOfStudent(studentId, groupId);
            modelAndView.setViewName("redirect:/show/group?groupId=" + groupId);//
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            modelAndView.setViewName(ERROR_PAGE.getPageName());
        }
        //add action
        return modelAndView;
    }

    @RequestMapping(value = "/subject/{subjectId}")
    @GetMapping
    public ModelAndView editSubject(@RequestParam("subjectName") String subjectName,
                                        @RequestParam("maxGrade") String maxGrade,
                                        @RequestParam("passProcGrade") String passProcGrade,
                                        @RequestParam("subjectDescription") String subjectDescription,
                                        @PathVariable String subjectId) {

        ModelAndView modelAndView = new ModelAndView();
        Subject subject = new Subject(
                subjectId,
                subjectName,
                Integer.parseInt(maxGrade),
                Integer.parseInt(passProcGrade),
                subjectDescription
        );

        try {
            daoSubject.updateSubject(subject);
            modelAndView.setViewName("redirect:/show/subject?subjectId="+subjectId);//
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            modelAndView.setViewName(ERROR_PAGE.getPageName());
        }
        //add action
        return modelAndView;
    }


    @RequestMapping(value = "/task")
    @GetMapping
    public ModelAndView editTask(@RequestParam("taskId") String taskId,
                                     @RequestParam("subjectId") String subjectId,
                                     @RequestParam("taskName") String taskName,
                                     @RequestParam("maxGrade") String maxGrade,
                                     @RequestParam("taskDescription") String taskDescription) {

        ModelAndView modelAndView = new ModelAndView();

        try {
            daoTask.updateTaskNameAndGrade(taskId, taskName, Integer.parseInt(maxGrade), taskDescription);
            daoSubject.actualizeMaxGrade(subjectId);
            modelAndView.setViewName("redirect:/show/subject?subjectId="+subjectId);//
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            modelAndView.setViewName(ERROR_PAGE.getPageName());
        }
        //add action
        return modelAndView;
    }

    @RequestMapping(value = "/student-task/{teacherId}")
    @GetMapping
    public ModelAndView editStudentTask(@RequestParam("taskId") String taskId,
                                     @RequestParam("studentSubjectId") String studentSubjectId,
                                     @RequestParam("grade") String grade,
                                     @PathVariable String teacherId) {

        ModelAndView modelAndView = new ModelAndView();
        StudentSubject studentSubject = null;
        try {
            daoStudentTask.updateStudentTaskGrade(taskId, studentSubjectId, Integer.parseInt(grade), teacherId);
            daoStudentSubject.actualizeTotalGrade(studentSubjectId);
            studentSubject = daoStudentSubject.getStudentSubjectById(studentSubjectId);
            modelAndView.setViewName("redirect:/show/student-task-list?subjectId=" +
                    studentSubject.getSubjectId() + "&studentId=" + studentSubject.getStudentId());
        } catch (SQLException | WrongEntityIdException throwables) {
            throwables.printStackTrace();
            modelAndView.setViewName(ERROR_PAGE.getPageName());
        }
        //add action
        return modelAndView;
    }

    ///------------------
    //-- try to modify
    @RequestMapping(value = "/user-password")
    @GetMapping
    public ModelAndView changeUserPassword(@RequestParam("newPassword") String newPassword,
                                           @RequestParam("confirmPassword") String confirmPassword,
                                           HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();
        //add all validation
        if(newPassword.equals(confirmPassword)) {
            HttpSession session = request.getSession(false);
            String currentUserId = (String) session.getAttribute(CURRENT_USER_ID.getSessionAttributeName());
            String currentUserRole = (String) session.getAttribute(CURRENT_USER_ROLE.getSessionAttributeName());

            User user = null;
            try {
                if (STUDENT.getRole().equals(currentUserRole)) {
                    daoStudent.changeStudentPassword(currentUserId, newPassword);
                    user = daoStudent.getStudentById(currentUserId);
                } else if (TEACHER.getRole().equals(currentUserRole)) {
                    daoTeacher.changeTeacherPassword(currentUserId, newPassword);
                    user = daoTeacher.getTeacherById(currentUserId);
                } else {
                    //some validation
                }
            } catch (SQLException | WrongEntityIdException throwables) {
                throwables.printStackTrace();
            }
            modelAndView.addObject("user", user);
            modelAndView.addObject("userRole", currentUserRole);
        }

        modelAndView.addObject("isCurrentProfile", "Yes");
        modelAndView.setViewName(PageName.PROFILE_PAGE.getPageName());
        return modelAndView;
    }
}
