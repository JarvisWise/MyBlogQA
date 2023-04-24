package org.example.controllers;

import org.example.entities.Group;
import org.example.entities.Student;
import org.example.entities.Teacher;
import org.example.entities.User;
import org.example.tools.strings.SessionAttributeName;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public abstract class AbstractController {
    protected void setBaseSessionVariables(HttpServletRequest request, User user) {
        HttpSession session = request.getSession(false);
        session.setAttribute(
                SessionAttributeName.CURRENT_USER_ID.getSessionAttributeName(),
                user.getUserId()
        );
        session.setAttribute(
                SessionAttributeName.CURRENT_USERNAME.getSessionAttributeName(),
                user.getFullName()
        );
        session.setAttribute(
                SessionAttributeName.CURRENT_USER_ROLE.getSessionAttributeName(),
                user.getRole()
        );
    }

    protected void removeStudentById(List<Student> studentList, final String studentId) {
        studentList.removeIf(s -> (s.getStudentId().equals(studentId)));
    }

    protected void removeStudentByList(List<Student> fromStudentList, final List<Student> removeStudentList) {
        for (Student s: removeStudentList) {
            removeStudentById(fromStudentList, s.getStudentId());
        }
    }

    protected void removeGroupById(List<Group> groupList, final String groupId) {
        groupList.removeIf(g -> (g.getGroupId().equals(groupId)));
    }

    protected void removeGroupByList(List<Group> fromGroupList, final List<Group> removeGroupList) {
        for (Group g: removeGroupList) {
            removeGroupById(fromGroupList, g.getGroupId());
        }
    }

    protected void removeTeacherById(List<Teacher> teacherList, final String teacherId) {
        teacherList.removeIf(g -> (g.getTeacherId().equals(teacherId)));
    }

    protected void removeTeacherByList(List<Teacher> fromTeacherList, final List<Teacher> removeTeacherList) {
        for (Teacher t: removeTeacherList) {
            removeTeacherById(fromTeacherList, t.getTeacherId());
        }
    }

    //protected ModelAndView createHeader()
}
