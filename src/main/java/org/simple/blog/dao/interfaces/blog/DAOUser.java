package org.simple.blog.dao.interfaces.blog;

import org.simple.blog.entities.blog.BlogUser;
import org.simple.blog.tools.custom.exceptions.WrongEntityIdException;
import org.simple.blog.tools.custom.exceptions.WrongLoginDataException;

import java.sql.SQLException;

public interface DAOUser {
    BlogUser getUserById(String id) throws WrongEntityIdException;
    boolean isExistUserByEmail(String email) throws SQLException;
    BlogUser getUserByEmailAndPassword(String email, String password) throws WrongLoginDataException;
    void updateUserCommon(String userId, String firstName, String lastName, String birthday) throws SQLException;
    void addUser(BlogUser blogUser) throws SQLException;
    void updateUser(BlogUser blogUser) throws SQLException;
    void setResetToken(String userId, String token) throws SQLException;
    BlogUser getUserByToken(String token) throws WrongEntityIdException;
    BlogUser getUserByEmail(String email) throws WrongEntityIdException;
    void setNewPassword(String userId, String password) throws SQLException;
    void deleteUserByEmail(String email);

}
