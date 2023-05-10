package org.simple.blog.dao.interfaces.blog;

import org.simple.blog.entities.blog.BlogPost;
import org.simple.blog.entities.blog.BlogPostSet;
import org.simple.blog.tools.custom.exceptions.WrongEntityIdException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface DAOPost {
    BlogPost getPostById(String id) throws WrongEntityIdException;
    List<BlogPost> getPostsByUserId(String userId) throws WrongEntityIdException;
    void addPost(BlogPost blogPost) throws SQLException;
    void updatePost(String postId, String postText, String postName, String postDateTime) throws SQLException;
    void updatePost(BlogPost blogPost) throws SQLException;
    List<BlogPost> getPostsByPage(Integer pageNumber) throws WrongEntityIdException;
    void deletePostTree(String postId);
    void deletePost(String postId);
    void deletePostComments(String postId);
    Integer getPageCount();
    List<BlogPostSet> getPostSet(List<BlogPost> blogList) throws WrongEntityIdException;
    List<BlogPostSet> getPostSetByPage(Integer pageNumber) throws WrongEntityIdException;
    Integer getCommentsCountByPost(String postId);
    HashMap<BlogPost, String> getPostWithCommentCount(List<BlogPost> postList);
    HashMap<BlogPost, String> getPostWithCommentCount(String userId) throws WrongEntityIdException;
    void deletePostsByUserId(String userId);
}
