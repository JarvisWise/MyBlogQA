package org.simple.blog.entities.blog;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BlogCommentSet {
    private BlogComment comment;
    private BlogUser commentUser;
    private BlogUser parentUser;
}
