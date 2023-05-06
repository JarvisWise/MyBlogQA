package org.simple.blog.tools.strings;

public enum AttributeName {
    CURRENT_USERNAME("current_username"),
    CURRENT_USER_ID("current_user_id"),
    EXCEPTION_MESSAGE("ExceptionMessage"),
    SUCCESS_MESSAGE("SuccessMessage"),
    END("");

    public final String attributeName;

    private AttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeName() {
        return attributeName;
    }
}
