package org.simple.blog.entities.blog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BlogPostSet {
    private BlogPost post;
    private BlogUser postUser;
}
