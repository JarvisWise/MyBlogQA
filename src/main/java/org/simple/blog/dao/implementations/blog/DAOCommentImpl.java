package org.simple.blog.dao.implementations.blog;

import org.apache.log4j.Logger;
import org.simple.blog.dao.connection.Oracle;
import org.simple.blog.dao.interfaces.blog.DAOComment;
import org.simple.blog.dao.interfaces.blog.DAOUser;
import org.simple.blog.entities.blog.BlogComment;
import org.simple.blog.entities.blog.BlogCommentSet;
import org.simple.blog.entities.blog.BlogUser;
import org.simple.blog.tools.custom.exceptions.WrongEntityIdException;
import org.simple.blog.tools.strings.Query;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DAOCommentImpl extends Oracle implements DAOComment {

    private static final Logger logger = Logger.getLogger(DAOCommentImpl.class);

    @Override
    public BlogComment getCommentById(String id) throws WrongEntityIdException {
        try {
            connect();
            statement = connection.prepareStatement(
                    Query.BLOG_COMMENT_BY_ID.getQuery());

            statement.setInt(1, Integer.parseInt(id));
            result = statement.executeQuery();
            if(result.next()) {
                return BlogComment.parse(result);
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
    public void addComment(BlogComment blogComment) throws SQLException {
        try {
            connect();
            statement = connection.prepareStatement(Query.ADD_BLOG_COMMENT.getQuery());
            statement.setString(1, blogComment.getCommentText());
            statement.setString(2, blogComment.getCommentDateTime());
            statement.setInt(3, Integer.parseInt(blogComment.getCommentUserId()));
            statement.setInt(4, Integer.parseInt(blogComment.getCommentPostId()));
            if (blogComment.getCommentParentId() == null || blogComment.getCommentParentId().isEmpty()) {
                statement.setNull(5, Types.INTEGER);
            } else {
                statement.setInt(5, Integer.parseInt(blogComment.getCommentParentId()));
            }
            statement.execute();
        } catch (SQLException e) {
            logger.warn(e);
            throw new SQLException("desc", e);
        } finally {
            disconnect();
        }
    }

    @Override
    public void updateComment(String commentId, String commentText, String commentDateTime) throws SQLException {
        try {
            connect();
            statement = connection.prepareStatement(Query.UPDATE_BLOG_COMMENT.getQuery());
            statement.setString(1, commentText);
            statement.setString(2, commentDateTime);
            statement.setInt(3, Integer.parseInt(commentId));
            statement.execute();
        } catch (SQLException e) {
            logger.warn(e);
            throw new SQLException("desc", e);
        } finally {
            disconnect();
        }
    }

    @Override
    public void updateComment(BlogComment blogComment) throws SQLException {
        try {
            connect();
            statement = connection.prepareStatement(Query.UPDATE_BLOG_COMMENT.getQuery());
            statement.setString(1, blogComment.getCommentText());
            statement.setString(2, blogComment.getCommentDateTime());
            statement.setInt(3, Integer.parseInt(blogComment.getCommentId()));
            statement.execute();
        } catch (SQLException e) {
            logger.warn(e);
            throw new SQLException("desc", e);
        } finally {
            disconnect();
        }
    }

    @Override
    public List<BlogComment> getCommentsByPostId(String postId) throws WrongEntityIdException {
        try {
            connect();
            java.util.List<BlogComment> list = new ArrayList<>();
            statement = connection.prepareStatement(Query.SELECT_COMMENTS_PER_POST.getQuery());
            statement.setInt(1, Integer.parseInt(postId));
            result = statement.executeQuery();
            while (result.next()) {
                list.add(BlogComment.parse(result));
            }
            return list;
        } catch (SQLException e) {
            logger.warn(e);
            throw new WrongEntityIdException("desc", e);
        } finally {
            disconnect();
        }
    }

    @Override
    public void deleteCommentTree(String commentId) {
        try {
            connect();
            statement = connection.prepareStatement(Query.DELETE_COMMENT_TREE_BY_COMMENT_ID.getQuery());
            statement.setInt(1, Integer.parseInt(commentId));
            statement.setInt(2, Integer.parseInt(commentId));
            statement.execute();
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            disconnect();
        }
    }

    @Override
    public BlogCommentSet getCommentSet(String commentId) throws WrongEntityIdException {
        DAOUser daoUser = new DAOUserImpl();

        BlogComment blogComment = getCommentById(commentId);
        BlogUser commentUser = daoUser.getUserById(blogComment.getCommentUserId());
        BlogUser parentUser = null;

        if (blogComment.getCommentParentId() != null
                && !"".equals(blogComment.getCommentParentId())) {
            parentUser = daoUser.getUserById(
                    getCommentById(blogComment.getCommentParentId())
                    .getCommentUserId()
            );
        }

        return new BlogCommentSet(blogComment, commentUser, parentUser);
    }

    @Override
    public List<BlogCommentSet> getCommentSetList(List<BlogComment> list) throws WrongEntityIdException {
        List<BlogCommentSet> blogCommentSet =  new ArrayList<>();
        for (BlogComment c: list) {
            blogCommentSet.add(getCommentSet(c.getCommentId()));
        }
        return blogCommentSet;
    }

    public List<BlogCommentSet> getCommentSetByPostId(String postId) throws WrongEntityIdException {
        List<BlogComment> blogComments = getCommentsByPostId(postId);
        return getCommentSetList(blogComments);
    }

    @Override
    public void deleteCommentsByUserId(String userId) {
        try {
            connect();
            statement = connection.prepareStatement(Query.DELETE_COMMENT_BY_USER_ID.getQuery());
            statement.setInt(1, Integer.parseInt(userId));
            statement.execute();
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            disconnect();
        }
    }
}
