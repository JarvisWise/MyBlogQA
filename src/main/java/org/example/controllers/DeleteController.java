package org.example.controllers;

import org.example.dao.implementations.*;
import org.example.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.example.tools.strings.PageName.ERROR_PAGE;

@Controller
@RequestMapping(value = "/delete")
public class DeleteController {

    private final DAOStudentImpl daoStudent;
    private final DAOTeacherImpl daoTeacher;
    private final DAOGroupImpl daoGroup;
    private final DAOSubjectImpl daoSubject;
    private final DAOTeacherSubjectImpl daoTeacherSubject;
    private final DAOStudentSubjectImpl daoStudentSubject;
    private final DAOStudentTaskImpl daoStudentTask;
    private final DAOTaskImpl daoTask;

    @Autowired
    public DeleteController(DAOStudentImpl daoStudent, DAOTeacherImpl daoTeacher, DAOGroupImpl daoGroup, DAOSubjectImpl daoSubject, DAOTeacherSubjectImpl daoTeacherSubject, DAOStudentSubjectImpl daoStudentSubject, DAOStudentTaskImpl daoStudentTask, DAOTaskImpl daoTask) {
        this.daoStudent = daoStudent;
        this.daoTeacher = daoTeacher;
        this.daoGroup = daoGroup;
        this.daoSubject = daoSubject;
        this.daoTeacherSubject = daoTeacherSubject;
        this.daoStudentSubject = daoStudentSubject;
        this.daoStudentTask = daoStudentTask;
        this.daoTask = daoTask;
    }

    @RequestMapping(value = "/group")
    @GetMapping
    public ModelAndView deleteByGroupId(@RequestParam("groupId") String groupId) {

        ModelAndView modelAndView = new ModelAndView();
        try {

            List<Student> studentList = daoStudent.getStudentsByGroupId(groupId);
            for (Student s: studentList) {
                daoStudent.deleteGroupOfStudent(s.getStudentId());
            }
            daoGroup.deleteGroup(groupId);
            modelAndView.setViewName("redirect:/show/group-all");
        } catch (Exception throwables) {
            throwables.printStackTrace();
            modelAndView.setViewName(ERROR_PAGE.getPageName());
        }

        return modelAndView;
    }

    @RequestMapping(value = "/student")
    @GetMapping
    public ModelAndView deleteByStudentId(@RequestParam("studentId") String studentId) {

        daoStudentTask.deleteStudentTasksByStudentId(studentId);
        daoStudentSubject.deleteStudentSubjectsByStudentId(studentId);
        daoStudent.deleteStudent(studentId);
        return new ModelAndView("redirect:/show/student-all");
    }

    @RequestMapping(value = "/teacher")
    @GetMapping
    public ModelAndView deleteByTeacherId(@RequestParam("teacherId") String teacherId) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            daoStudentTask.deleteByTeacherId(teacherId);
            daoTeacherSubject.deleteTeacherSubjectsByTeacherId(teacherId);
            daoTeacher.deleteTeacher(teacherId);
            modelAndView.setViewName("redirect:/show/teacher-all");
        } catch (Exception throwables) {
            throwables.printStackTrace();
            modelAndView.setViewName(ERROR_PAGE.getPageName());
        }

        return modelAndView;
    }

    @RequestMapping(value = "/subject")
    @GetMapping
    public ModelAndView deleteBySubjectId(@RequestParam("subjectId") String subjectId) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            daoStudentTask.deleteStudentTasksBySubjectId(subjectId);
            daoTask.deleteTasksBySubjectId(subjectId);
            daoTeacherSubject.deleteTeacherSubjectsBySubjectId(subjectId);
            daoStudentSubject.deleteStudentSubjectsBySubjectId(subjectId);
            daoSubject.deleteSubject(subjectId);
            modelAndView.setViewName("redirect:/show/subject-all");
        } catch (Exception throwables) {
            throwables.printStackTrace();
            modelAndView.setViewName(ERROR_PAGE.getPageName());
        }

        return modelAndView;
    }

    @RequestMapping(value = "/teacher-subject")
    @GetMapping
    public ModelAndView deleteTeacherSubjectById(@RequestParam("teacherId") String teacherId,
                                                 @RequestParam("subjectId") String subjectId) {

        ModelAndView modelAndView = new ModelAndView();
        TeacherSubject teacherSubject = new TeacherSubject(
                subjectId,
                teacherId
        );

        try {
            daoTeacherSubject.deleteTeacherSubject(teacherSubject);
            modelAndView.setViewName("redirect:/show/subject?subjectId=" + subjectId);//
        } catch (Exception throwables) {
            throwables.printStackTrace();
            modelAndView.setViewName(ERROR_PAGE.getPageName());
        }

        return modelAndView;
    }

    @RequestMapping(value = "/student-subject")
    @GetMapping
    public ModelAndView deleteStudentSubjectById(@RequestParam("studentId") String studentId,
                                                 @RequestParam("subjectId") String subjectId) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            StudentSubject studentSubject = daoStudentSubject.getStudentSubjectBySubjectIdAndStudentId(subjectId, studentId);
            daoStudentTask.deleteStudentTasksByStudentSubjectId(studentSubject.getStudentSubjectId());
            daoStudentSubject.deleteStudentSubject(studentId, subjectId);

            modelAndView.setViewName("redirect:/show/subject?subjectId=" + subjectId);//
        } catch (Exception throwables) {
            throwables.printStackTrace();
            modelAndView.setViewName(ERROR_PAGE.getPageName());
        }

        return modelAndView;
    }

    @RequestMapping(value = "/student-group")
    @GetMapping
    public ModelAndView deleteStudentGroup(@RequestParam("studentId") String studentId,
                                                 @RequestParam("groupId") String groupId) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            daoStudent.deleteGroupOfStudent(studentId);
            modelAndView.setViewName("redirect:/show/group?groupId=" + groupId);//
        } catch (Exception throwables) {
            throwables.printStackTrace();
            modelAndView.setViewName(ERROR_PAGE.getPageName());
        }

        return modelAndView;
    }

    @RequestMapping(value = "/task")
    @GetMapping
    public ModelAndView deleteByTaskId(@RequestParam("taskId") String taskId,
                                       @RequestParam("subjectId") String subjectId) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            List<StudentSubject> studentSubjectList = daoStudentSubject.getStudentSubjectsBySubjectId(subjectId);
            daoStudentTask.deleteStudentTasksByTaskId(taskId);
            daoTask.deleteTask(taskId);
            daoSubject.actualizeMaxGrade(subjectId);
            for (StudentSubject s: studentSubjectList) {
                daoStudentSubject.actualizeTotalGrade(s.getStudentSubjectId());
            }
            modelAndView.setViewName("redirect:/show/subject?subjectId=" + subjectId);
        } catch (Exception throwables) {
            throwables.printStackTrace();
            modelAndView.setViewName(ERROR_PAGE.getPageName());
        }

        return modelAndView;
    }
}
