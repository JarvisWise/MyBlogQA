package org.simple.blog.entities.blog;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BlogUser {

    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String birthday;
    private String resetToken;


    public BlogUser(String userId, String email, String firstName, String lastName, String password, String birthday) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.birthday = birthday;
        this.resetToken = null;
    }

    public  static BlogUser parse(ResultSet result) throws SQLException {
        return new BlogUser (
                result.getString("user_id"),
                result.getString("email"),
                result.getString("first_name"),
                result.getString("last_name"),
                result.getString("password"),
                result.getString("birthday"),
                result.getString("reset_token")
        );
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }
}
