package org.simple.blog.tools;

import java.time.format.DateTimeFormatter;

public class BlogConstants {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    public static final Integer PAGINATION_POST_BY_PAGE = 6;

    //(not used) Specify your database connection information
    /*public static final String DB_CONTEXT_FACTORY = "weblogic.jndi.WLInitialContextFactory";
    public static final String DB_PROVIDER_URL = "t3://localhost:7001";
    public static final String DB_DATA_SOURCE_NAME = "MyData";*/

    //(not used) This works for local test with MailHog, modify values for your SMTP server
    /*public static final String MAIL_ADDRESS = "too.simple.send.mail@gmail.com";
    public static final String MAIL_PASSWORD = "1234Pass5@#";
    public static final String MAIL_HOST = "localhost";
    public static final String MAIL_PORT = "1025";
    public static final boolean MAIL_SSL = true;
    public static final boolean MAIL_AUTH = true;
    public static final String APP_URL = "http://localhost:7001//simple-blog/";
    public static final String RESET_URL = APP_URL + "redirect/reset-password?token=";*/
}
