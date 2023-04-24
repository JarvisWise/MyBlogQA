package org.example.controllers;

import org.example.dao.implementations.DAOGroupImpl;
import org.example.dao.implementations.DAOStudentImpl;
import org.example.dao.implementations.DAOSubjectImpl;
import org.example.entities.Group;
import org.example.entities.Student;
import org.example.entities.Subject;
import org.example.tools.custom.exceptions.WrongEntityIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping(value = "/search")
public class SearchController {

    private final DAOStudentImpl daoStudent;
    private final DAOGroupImpl daoGroup;
    private final DAOSubjectImpl daoSubject;

    @Autowired
    public SearchController(DAOStudentImpl daoStudent, DAOGroupImpl daoGroup, DAOSubjectImpl daoSubject) {
        this.daoStudent = daoStudent;
        this.daoGroup = daoGroup;
        this.daoSubject = daoSubject;
    }

    @RequestMapping(value = "/by-student-full-name")
    @GetMapping
    public @ResponseBody String searchByStudentFullName(@RequestParam("studentFullName") String studentFullName) {
        String result;
        try {
            List<Student> studentSearchList = daoStudent.searchStudentsByFullName(studentFullName);
            result = studentSearchListBuild(studentSearchList);
        } catch (WrongEntityIdException | SQLException throwable) {
            throwable.printStackTrace();//--
            result = "Error!";
        }
        return result;
    }

    @RequestMapping(value = "/by-group-name")
    @GetMapping
    public @ResponseBody String searchByGroupName(@RequestParam("groupName") String groupName) {
        String result;
        try {
            List<Group> groupSearchList = daoGroup.searchGroupsByName(groupName);
            result = groupSearchListBuild(groupSearchList);
        } catch (WrongEntityIdException | SQLException throwable) {
            throwable.printStackTrace();//--
            result = "Error!";
        }
        return result;
    }

    @RequestMapping(value = "/by-subject-name")
    @GetMapping
    public @ResponseBody String searchBySubjectName(@RequestParam("subjectName") String subjectName) {
        String result;
        try {
            List<Subject> subjectSearchList = daoSubject.searchSubjectsByName(subjectName);
            result = subjectSearchListBuild(subjectSearchList);
        } catch (WrongEntityIdException | SQLException throwable) {
            throwable.printStackTrace();//--
            result = "Error!";
        }
        return result;
    }

    //for list form
    public String studentSearchListBuild(List<Student> studentSearchList) {
        StringBuilder result = new StringBuilder();
        for (Student st: studentSearchList) {
            /*if(!result.toString().isEmpty()) {
                result.append("<br/>");
            }*/

            result.append("<li><a href=\"/Lab3GradeBook/redirect/profile?userId=")
                    .append(st.getStudentId()).append("&userRole=").append(st.getRole()).append("\">")
                    //.append(st.getFirstName()).append(" ").append(st.getLastName())
                    //.append(" (").append(st.getEmail()).append(")\t")
                    .append(st.getFullNameWithEmail())
                    .append("</a></li>");

            /*Group group;
            try {
                group = daoGroup.getGroupById(st.getGroupId());
            } catch (WrongEntityIdException e) {
                group = null;
            }

            if (group != null) {
                result.append(" <label class='greyClass'>(").append(group.getGroupName()).append(")</label>");
            } else {
                result.append(" <label class='greyClass'>(No group yet)</label>");
            }*/
        }
        return result.toString();
    }

    public String groupSearchListBuild(List<Group> groupSearchList) {
        StringBuilder result = new StringBuilder();
        for (Group gr: groupSearchList) {
            /*if(!result.toString().isEmpty()) {
                result.append("<br/>");
            }*/

            result.append("<li><a href=\"/Lab3GradeBook/show/group?groupId=")
                    .append(gr.getGroupId()).append("\">")
                    .append(gr.getGroupName()).append("</a></li>");
        }
        return result.toString();
    }

    public String subjectSearchListBuild(List<Subject> subjectSearchList) {
        StringBuilder result = new StringBuilder();
        for (Subject s: subjectSearchList) {
            /*if(!result.toString().isEmpty()) {
                result.append("<br/>");
            }*/

            result.append("<li><a href=\"/Lab3GradeBook/show/subject?subjectId=")
                    .append(s.getSubjectId()).append("\">")
                    .append(s.getSubjectName()).append("</a></li>");
        }
        return result.toString();
    }
}
