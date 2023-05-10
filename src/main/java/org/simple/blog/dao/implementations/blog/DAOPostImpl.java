package org.simple.blog.dao.implementations.blog;

import org.apache.log4j.Logger;
import org.simple.blog.dao.connection.Oracle;
import org.simple.blog.dao.interfaces.blog.DAOPost;
import org.simple.blog.dao.interfaces.blog.DAOUser;
import org.simple.blog.entities.blog.BlogPost;
import org.simple.blog.entities.blog.BlogPostSet;
import org.simple.blog.entities.blog.BlogUser;
import org.simple.blog.tools.custom.exceptions.WrongEntityIdException;
import org.simple.blog.tools.strings.Query;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.simple.blog.tools.BlogConstants.PAGINATION_POST_BY_PAGE;

@Repository
public class DAOPostImpl extends Oracle implements DAOPost {

    private static final Logger logger = Logger.getLogger(DAOPostImpl.class);



    @Override
    public BlogPost getPostById(String id) throws WrongEntityIdException {
        try {
            connect();
            statement = connection.prepareStatement(
                    Query.BLOG_POST_BY_ID.getQuery());

            statement.setInt(1, Integer.parseInt(id));
            result = statement.executeQuery();
            if(result.next()) {
                return BlogPost.parse(result);
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
    public List<BlogPost> getPostsByUserId(String userId) throws WrongEntityIdException {

        try {
            connect();
            ArrayList<BlogPost> posts =  new ArrayList<>();
            statement = connection.prepareStatement(
                    Query.BLOG_POSTS_BY_USER_ID.getQuery());

            statement.setInt(1, Integer.parseInt(userId));
            result = statement.executeQuery();
            while(result.next()) {
                posts.add(BlogPost.parse(result));
            }

            return posts;
        } catch (SQLException e) {
            logger.warn(e);
            throw new WrongEntityIdException("desc ", e);
        } finally {
            disconnect();
        }

    }

    @Override
    public void addPost(BlogPost blogPost) throws SQLException {
        try {
            connect();
            statement = connection.prepareStatement(Query.ADD_BLOG_POST.getQuery());
            statement.setString(1, blogPost.getPostText());
            statement.setString(2, blogPost.getPostName());
            statement.setString(3, blogPost.getPostDateTime());
            statement.setInt(4, Integer.parseInt(blogPost.getPostUserId()));
            statement.execute();
        } catch (SQLException e) {
            logger.warn(e);
            throw new SQLException("desc", e);
        } finally {
            disconnect();
        }
    }

    @Override
    public void updatePost(String postId, String postText, String postName, String postDateTime) throws SQLException {
        try {
            connect();
            statement = connection.prepareStatement(Query.UPDATE_BLOG_POST.getQuery());
            statement.setString(1, postText);
            statement.setString(2, postName);
            statement.setString(3, postDateTime);
            statement.setInt(4, Integer.parseInt(postId));
            statement.execute();
        } catch (SQLException e) {
            logger.warn(e);
            throw new SQLException("desc", e);
        } finally {
            disconnect();
        }
    }

    @Override
    public void updatePost(BlogPost blogPost) throws SQLException {
        try {
            connect();
            statement = connection.prepareStatement(Query.UPDATE_BLOG_POST.getQuery());
            statement.setString(1, blogPost.getPostText());
            statement.setString(2, blogPost.getPostName());
            statement.setString(3, blogPost.getPostDateTime());
            statement.setInt(4, Integer.parseInt(blogPost.getPostId()));
            statement.execute();
        } catch (SQLException e) {
            logger.warn(e);
            throw new SQLException("desc", e);
        } finally {
            disconnect();
        }
    }

    //count from 1
    @Override
    public List<BlogPost> getPostsByPage(Integer pageNumber) throws WrongEntityIdException {
        try {
            connect();
            java.util.List<BlogPost> list = new ArrayList<>();
            statement = connection.prepareStatement(Query.SELECT_BLOG_POSTS_PER_PAGE.getQuery());
            statement.setInt(1, (pageNumber - 1) * PAGINATION_POST_BY_PAGE);
            statement.setInt(2, PAGINATION_POST_BY_PAGE);
            result = statement.executeQuery();
            while (result.next()) {
                list.add(BlogPost.parse(result));
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
    public List<BlogPostSet> getPostSet(List<BlogPost> blogList) throws WrongEntityIdException {
        DAOUser daoUser = new DAOUserImpl();
        ArrayList<BlogPostSet> postSets =  new ArrayList<>();

        for (BlogPost post: blogList) {
            BlogUser user = daoUser.getUserById(post.getPostUserId());
            postSets.add(new BlogPostSet(post, user));
        }

        return postSets;
    }

    @Override
    public List<BlogPostSet> getPostSetByPage(Integer pageNumber) throws WrongEntityIdException {
        return getPostSet(getPostsByPage(pageNumber));
    }

    @Override
    public void deletePostTree(String postId) {
        deletePostComments(postId);
        deletePost(postId);
    }

    @Override
    public void deletePost(String postId) {
        try {
            connect();
            statement = connection.prepareStatement(Query.DELETE_POST_BY_POST_ID.getQuery());
            statement.setInt(1, Integer.parseInt(postId));
            statement.execute();
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            disconnect();
        }
    }

    @Override
    public void deletePostComments(String postId) {
        try {
            connect();
            statement = connection.prepareStatement(Query.DELETE_COMMENTS_BY_POST_ID.getQuery());
            statement.setInt(1, Integer.parseInt(postId));
            statement.execute();
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            disconnect();
        }
    }

    @Override
    public Integer getPageCount() {
        try {
            connect();
            statement = connection.prepareStatement(Query.POST_COUNT.getQuery());
            result = statement.executeQuery();
            if (result.next()) {
                Integer count = Integer.parseInt(result.getString("count"));
                if (count % PAGINATION_POST_BY_PAGE > 0) {
                    return (count / PAGINATION_POST_BY_PAGE) + 1;
                } else {
                    return count / PAGINATION_POST_BY_PAGE;
                }
            }
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            disconnect();
        }
        return 1;
    }

    @Override
    public Integer getCommentsCountByPost(String postId) {
        int count = 0;

        try {
            connect();
            statement = connection.prepareStatement(Query.COMMENTS_BY_POST_COUNT.getQuery());
            statement.setInt(1, Integer.parseInt(postId));
            result = statement.executeQuery();
            if (result.next()) {
                count = Integer.parseInt(result.getString("count"));
            }
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            disconnect();
        }
        return count;
    }

    @Override
    public HashMap<BlogPost, String> getPostWithCommentCount(List<BlogPost> postList) {
        HashMap<BlogPost, String> map = new HashMap<>();
        for (BlogPost post: postList) {
            map.put(post, getCommentsCountByPost(post.getPostId()).toString());
        }
        return map;
    }

    @Override
    public HashMap<BlogPost, String> getPostWithCommentCount(String userId) throws WrongEntityIdException {
        List<BlogPost> userPosts = getPostsByUserId(userId);
        return getPostWithCommentCount(userPosts);
    }

    @Override
    public void deletePostsByUserId(String userId) {
        try {
            connect();
            statement = connection.prepareStatement(Query.DELETE_POST_BY_USER_ID.getQuery());
            statement.setInt(1, Integer.parseInt(userId));
            statement.execute();
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            disconnect();
        }
    }
}
