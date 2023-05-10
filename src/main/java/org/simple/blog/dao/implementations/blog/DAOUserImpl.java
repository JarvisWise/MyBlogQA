package org.simple.blog.dao.implementations.blog;

import org.apache.log4j.Logger;
import org.simple.blog.dao.connection.Oracle;
import org.simple.blog.dao.interfaces.blog.DAOUser;
import org.simple.blog.entities.blog.BlogUser;
import org.simple.blog.tools.custom.exceptions.WrongEntityIdException;
import org.simple.blog.tools.custom.exceptions.WrongLoginDataException;
import org.simple.blog.tools.strings.Query;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;

@Repository
public class DAOUserImpl extends Oracle implements DAOUser {

    private static final Logger logger = Logger.getLogger(DAOUserImpl.class);

    @Override
    public BlogUser getUserById(String id) throws WrongEntityIdException {
        try {
            connect();
            statement = connection.prepareStatement(
                    Query.BLOG_USER_BY_ID.getQuery());

            statement.setInt(1, Integer.parseInt(id));
            result = statement.executeQuery();
            if(result.next()) {
                return BlogUser.parse(result);
            } else {
                throw new WrongEntityIdException("desc ");
            }
        } catch (SQLException | WrongEntityIdException e) {
            logger.warn(e);
            throw new WrongEntityIdException("desc ", e);
        } finally {
            disconnect();
        }
    }

    @Override
    public boolean isExistUserByEmail(String email) throws SQLException {
        try {
            connect();
            statement = connection.prepareStatement(
                    Query.EXIST_BLOG_USER_BY_EMAIL.getQuery());

            statement.setString(1, email);
            result = statement.executeQuery();
            return result.next();
        } catch (SQLException e) {
            logger.warn(e);
            throw new SQLException("desc ", e);
        } finally {
            disconnect();
        }
    }

    @Override
    public BlogUser getUserByEmailAndPassword(String email, String password) throws WrongLoginDataException {
        try {
            connect();
            statement = connection.prepareStatement(
                    Query.BLOG_USER_BY_EMAIL_AND_PASSWORD.getQuery());

            statement.setString(1, email);
            statement.setString(2, password);

            result = statement.executeQuery();
            if(result.next()) {
                return BlogUser.parse(result);
            } else {
                throw new WrongLoginDataException("desc ");
            }
        } catch (SQLException | WrongLoginDataException e) {
            logger.warn(e);
            throw new WrongLoginDataException("desc ", e);
        } finally {
            disconnect();
        }
    }

    @Override
    public void addUser(BlogUser blogUser) throws SQLException {
        try {
            connect();
            statement = connection.prepareStatement(Query.ADD_BLOG_USER.getQuery());
            statement.setString(1, blogUser.getEmail());
            statement.setString(2, blogUser.getFirstName());
            statement.setString(3, blogUser.getLastName());
            statement.setString(4, blogUser.getPassword());
            statement.setString(5, blogUser.getBirthday());
            statement.execute();
        } catch (SQLException e) {
            logger.warn(e);
            throw new SQLException("desc", e);
        } finally {
            disconnect();
        }
    }

    @Override
    public void updateUserCommon(String userId, String firstName, String lastName, String birthday) throws SQLException {
        try {
            connect();
            statement = connection.prepareStatement(Query.UPDATE_BLOG_USER_COMMON.getQuery());
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, birthday);
            statement.setInt(4, Integer.parseInt(userId));
            statement.execute();
        } catch (SQLException e) {
            logger.warn(e);
            throw new SQLException("desc", e);
        } finally {
            disconnect();
        }
    }

    @Override
    public void updateUser(BlogUser blogUser) throws SQLException {
        try {
            connect();
            statement = connection.prepareStatement(Query.UPDATE_BLOG_USER_COMMON.getQuery());
            statement.setString(1, blogUser.getFirstName());
            statement.setString(2, blogUser.getLastName());
            statement.setString(3, blogUser.getBirthday());
            statement.setInt(4, Integer.parseInt(blogUser.getUserId()));
            statement.execute();
        } catch (SQLException e) {
            logger.warn(e);
            throw new SQLException("desc", e);
        } finally {
            disconnect();
        }
    }

    @Override
    public void setResetToken(String userId, String token) throws SQLException {
        try {
            connect();
            statement = connection.prepareStatement(Query.SET_RESET_PASSWORD_TOKEN.getQuery());
            if (token == null) {
                statement.setNull(1, Types.VARCHAR);
            } else {
                statement.setString(1, token);
            }
            statement.setInt(2, Integer.parseInt(userId));
            statement.execute();
        } catch (SQLException e) {
            logger.warn(e);
            throw new SQLException("desc", e);
        } finally {
            disconnect();
        }
    }

    @Override
    public BlogUser getUserByToken(String token) throws WrongEntityIdException {
        try {
            connect();
            statement = connection.prepareStatement(
                    Query.GET_USER_BY_RESET_PASSWORD_TOKEN.getQuery());

            statement.setString(1, token);
            result = statement.executeQuery();
            if(result.next()) {
                return BlogUser.parse(result);
            } else {
                throw new WrongEntityIdException("desc ");
            }
        } catch (SQLException | WrongEntityIdException e) {
            logger.warn(e);
            throw new WrongEntityIdException("desc ", e);
        } finally {
            disconnect();
        }
    }

    @Override
    public BlogUser getUserByEmail(String email) throws WrongEntityIdException {
        try {
            connect();
            statement = connection.prepareStatement(
                    Query.GET_USER_BY_EMAIL.getQuery());

            statement.setString(1, email);
            result = statement.executeQuery();
            if(result.next()) {
                return BlogUser.parse(result);
            } else {
                throw new WrongEntityIdException("desc ");
            }
        } catch (SQLException | WrongEntityIdException e) {
            logger.warn(e);
            throw new WrongEntityIdException("desc ", e);
        } finally {
            disconnect();
        }
    }

    @Override
    public void setNewPassword(String userId, String password) throws SQLException {
        try {
            connect();
            statement = connection.prepareStatement(Query.SET_NEW_PASSWORD.getQuery());
            statement.setString(1, password);
            statement.setInt(2, Integer.parseInt(userId));
            statement.execute();
        } catch (SQLException e) {
            logger.warn(e);
            throw new SQLException("desc", e);
        } finally {
            disconnect();
        }
    }

    @Override
    public void deleteUserByEmail(String email) {
        try {
            connect();
            statement = connection.prepareStatement(Query.DELETE_USER_BY_EMAIL.getQuery());
            statement.setString(1, email);
            statement.execute();
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            disconnect();
        }
    }
}
