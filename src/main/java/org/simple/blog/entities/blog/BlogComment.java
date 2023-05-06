package org.simple.blog.entities.blog;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BlogComment {
    String commentId;
    String commentText;
    String commentDateTime;
    String commentUserId;
    String commentPostId;
    String commentParentId;
    String version;

    public  static BlogComment parse(ResultSet result) throws SQLException {
        return new BlogComment(
                result.getString("comment_id"),
                result.getString("comment_text"),
                result.getString("comment_date_time"),
                result.getString("comment_user_id"),
                result.getString("comment_post_id"),
                result.getString("comment_parent_id"),
                result.getString("version")
        );
    }
}
