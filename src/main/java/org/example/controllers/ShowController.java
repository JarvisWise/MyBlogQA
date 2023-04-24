package org.example.controllers;

import org.example.dao.implementations.*;
import org.example.entities.*;
import org.example.tools.custom.exceptions.WrongEntityIdException;
import org.example.tools.strings.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

import static org.example.tools.strings.PageName.*;

@Controller
@RequestMapping(value = "/show")
public class ShowController extends AbstractController{

    private final DAOStudentImpl daoStudent;
    private final DAOTeacherImpl daoTeacher;
    private final DAOGroupImpl daoGroup;
    private final DAOSubjectImpl daoSubject;
    private final DAOTaskImpl daoTask;
    private final DAOStudentTaskImpl daoStudentTask;
    private final DAOStudentSubjectImpl daoStudentSubject;

    @Autowired
    public ShowController(DAOStudentImpl daoStudent, DAOTeacherImpl daoTeacher, DAOGroupImpl daoGroup, DAOSubjectImpl daoSubject, DAOTaskImpl daoTask, DAOStudentTaskImpl daoStudentTask, DAOStudentSubjectImpl daoStudentSubject) {
        this.daoStudent = daoStudent;
        this.daoTeacher = daoTeacher;
        this.daoGroup = daoGroup;
        this.daoSubject = daoSubject;
        this.daoTask = daoTask;
        this.daoStudentTask = daoStudentTask;
        this.daoStudentSubject = daoStudentSubject;
    }

    @RequestMapping(value = "/group")
    @GetMapping
    public ModelAndView showByGroupId(@RequestParam("groupId") String groupId) {

        ModelAndView modelAndView = new ModelAndView();
        Group group = null;
        List<Student> studentList = null;
        List<Student> availableStudents = null;

        try {
            group = daoGroup.getGroupById(groupId);
            studentList = daoStudent.getStudentsByGroupId(groupId);
            //
            availableStudents = daoStudent.getAllStudents();
            removeStudentByList(availableStudents, studentList);
        } catch (WrongEntityIdException e) {
            e.printStackTrace();
        }

        modelAndView.addObject("group", group);
        modelAndView.addObject("studentList", studentList);
        modelAndView.addObject("availableStudents", availableStudents);
        modelAndView.setViewName("show-group-page");
        return modelAndView;
    }

    @RequestMapping(value = "/student")
    @GetMapping
    public ModelAndView showByStudentId(@RequestParam("studentId") String studentId) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/redirect/profile?userId=" +
                studentId + "&userRole=" + Role.STUDENT.getRole());//
        return modelAndView;
    }

    @RequestMapping(value = "/teacher")
    @GetMapping
    public ModelAndView showByTeacherId(@RequestParam("teacherId") String teacherId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/redirect/profile?userId=" +
                teacherId + "&userRole=" + Role.TEACHER.getRole());//
        return modelAndView;
    }

    @RequestMapping(value = "/subject")
    @GetMapping
    public ModelAndView showBySubjectId(@RequestParam("subjectId") String subjectId) {

        ModelAndView modelAndView = new ModelAndView();
        Subject subject = null;
        HashMap<Student, StudentSubject> studentSubjectMap = null;
        List<Teacher> teacherList = null;
        List<Student> availableStudents = null;
        List<Teacher> availableTeachers = null;
        List<Task> taskList = null;//

        try {
            subject = daoSubject.getSubjectById(subjectId);
            studentSubjectMap = daoStudentSubject.getStudentsInfoBySubjectId(subjectId);
            //mb optimize
            availableStudents = daoStudent.getAllStudents();
            removeStudentByList(availableStudents, daoSubject.getStudentsBySubjectId(subjectId));
            //
            teacherList = daoTeacher.getTeachersBySubjectId(subjectId);
            //mb optimize
            availableTeachers = daoTeacher.getAllTeachers();
            removeTeacherByList(availableTeachers, teacherList);
            //
            taskList = daoTask.getTaskListBySubjectId(subjectId);
        } catch (WrongEntityIdException e) {
            e.printStackTrace();
        }

        //
        modelAndView.addObject("availableStudents", availableStudents);
        modelAndView.addObject("availableTeachers", availableTeachers);
        //
        modelAndView.addObject("taskList", taskList);
        modelAndView.addObject("subject", subject);
        modelAndView.addObject("studentSubjectMap", studentSubjectMap);
        modelAndView.addObject("teacherList", teacherList);
        modelAndView.setViewName("show-subject-page");
        return modelAndView;
    }

    @RequestMapping(value = "/student-task-list")
    @GetMapping
    public ModelAndView showStudentTaskList(@RequestParam("subjectId") String subjectId,
                                            @RequestParam("studentId") String studentId) {

        ModelAndView modelAndView = new ModelAndView();
        Student student = null;
        Subject subject = null;
        StudentSubject studentSubject = null;
        List<StudentTaskInfoSet> studentTaskInfoMap = null;
        /*HashMap<Task, StudentTask> studentTaskMap = null;*/

        try {
            subject = daoSubject.getSubjectById(subjectId);
            student = daoStudent.getStudentById(studentId);
            studentSubject = daoStudentSubject.getStudentSubjectBySubjectIdAndStudentId(subjectId, studentId);
            studentTaskInfoMap = daoStudentTask.
                    getStudentTaskInfoSetList(studentSubject.getStudentSubjectId());
            /*studentTaskMap = daoStudentTask.
                    getStudentTasksInfoByStudentSubjectId(studentSubject.getStudentSubjectId());*/
        } catch (WrongEntityIdException e) {
            e.printStackTrace();
        }

        modelAndView.addObject("student", student);
        modelAndView.addObject("subject", subject);
        modelAndView.addObject("studentSubject", studentSubject);
        /*modelAndView.addObject("studentTaskMap", studentTaskMap);*/
        modelAndView.addObject("studentTaskInfoMap", studentTaskInfoMap);
        modelAndView.setViewName("student-task-list");
        return modelAndView;
    }

    //--------------show all
    @RequestMapping(value = "/student-all")
    @GetMapping
    public ModelAndView showAllStudent(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            List<Student> studentAllList = daoStudent.getAllStudents();
            List<StudentInfoSet> studentInfoSetList = daoStudent.getStudentInfoSetList(studentAllList);
            //--time solution
            //HttpSession session = request.getSession(false);
            //String currentRole = (String)session.getAttribute(CURRENT_USER_ROLE.getSessionAttributeName());
            //modelAndView.addObject("userType", currentRole);
            //--time solution
            //--and mb remove from [studentAllList] list current user
            //TO DO: remove current student from LIST ???


            modelAndView.addObject("studentInfoSetList", studentInfoSetList);
            modelAndView.setViewName(STUDENT_LIST_PAGE.getPageName());
        } catch (WrongEntityIdException e) {
            //add to error_page params
            modelAndView.setViewName(ERROR_PAGE.getPageName());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/group-all")
    @GetMapping
    public ModelAndView showAllGroup(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            List<Group> groupAllList = daoGroup.getAllGroups();

            //--time solution
            //HttpSession session = request.getSession(false);
            //String currentRole = (String)session.getAttribute(CURRENT_USER_ROLE.getSessionAttributeName());
            //modelAndView.addObject("userType", currentRole);
            //--time solution

            modelAndView.addObject("groups", groupAllList);
            modelAndView.setViewName(GROUP_LIST_PAGE.getPageName());
        } catch (WrongEntityIdException e) {
            //add to error_page params
            modelAndView.setViewName(ERROR_PAGE.getPageName());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/subject-all")
    @GetMapping
    public ModelAndView showAllSubject(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        try {
            List<Subject> subjectAllList = daoSubject.getAllSubjects();
            modelAndView.addObject("subjects", subjectAllList);
            modelAndView.setViewName(SUBJECT_LIST_PAGE.getPageName());
        } catch (WrongEntityIdException e) {
            //add to error_page params
            modelAndView.setViewName(ERROR_PAGE.getPageName());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/teacher-all")
    @GetMapping
    public ModelAndView showAllTeacher(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        try {
            List<Teacher> teacherAllList = daoTeacher.getAllTeachers();
            modelAndView.addObject("teachers", teacherAllList);
            modelAndView.setViewName(TEACHER_LIST_PAGE.getPageName());
        } catch (WrongEntityIdException e) {
            //add to error_page params
            modelAndView.setViewName(ERROR_PAGE.getPageName());
        }
        return modelAndView;
    }
}
