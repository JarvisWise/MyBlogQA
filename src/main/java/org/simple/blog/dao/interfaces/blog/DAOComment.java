package org.simple.blog.dao.interfaces.blog;

import org.simple.blog.entities.blog.BlogComment;
import org.simple.blog.entities.blog.BlogCommentSet;
import org.simple.blog.tools.custom.exceptions.WrongEntityIdException;

import java.sql.SQLException;
import java.util.List;

public interface DAOComment {
    BlogComment getCommentById(String id) throws WrongEntityIdException;
    void addComment(BlogComment blogComment) throws SQLException;
    void updateComment(String commentId, String commentText, String commentDateTime) throws SQLException;
    void updateComment(BlogComment blogComment) throws SQLException;
    List<BlogComment> getCommentsByPostId(String postId) throws WrongEntityIdException;
    void deleteCommentTree(String commentId);
    BlogCommentSet getCommentSet(String commentId) throws WrongEntityIdException;
    List<BlogCommentSet> getCommentSetList(List<BlogComment> list) throws WrongEntityIdException;
    List<BlogCommentSet> getCommentSetByPostId(String postId) throws WrongEntityIdException;
    void deleteCommentsByUserId(String userId);
}
