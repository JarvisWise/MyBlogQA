package org.simple.blog.tools.strings;

public enum PageName {
    ERROR_PAGE("error-page"),
    LOGIN_PAGE("login"),
    REGISTRATION_PAGE("registration"),
    BLOG_PROFILE_PAGE("blog-profile"),
    BLOG_FORGOT_PASSWORD_PAGE("blog-forgot-password"),
    BLOG_RESET_PASSWORD_PAGE("blog-reset-password"),
    ADD_POST_PAGE("add-post-page"),
    SHOW_POST_PAGE("show-post-page"),
    SHOW_POSTS_PAGE("show-posts-page"),
    END("");

    public final String pageName;

    private PageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageName() {
        return pageName;
    }
}
