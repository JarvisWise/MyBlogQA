package org.simple.blog.tools;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties {

    private static boolean isRead = false;
    private static final Logger logger = Logger.getLogger(AppProperties.class);

    private static String dbContextFactory;
    private static String dbProviderUrl;
    private static String dbDataSourceName;

    private static String mailAddress;
    private static String mailPassword;
    private static String mailHost;
    private static String mailPort;
    private static boolean mailSSL;
    private static boolean mailAuth;
    private static String mailAppUrl;
    private static String mailResetUrl;

    private AppProperties() {}

    public static void readProperties() {
        try (InputStream input = AppProperties.class.getClassLoader().getResourceAsStream("app.properties")) {

            if (input == null) {
                logger.error("Failed to load properties!");
                return;
            }

            Properties prop = new Properties();
            prop.load(input);
            isRead = true;
            dbContextFactory = prop.getProperty("db.context.factory");
            dbProviderUrl = prop.getProperty("db.provider.url");
            dbDataSourceName = prop.getProperty("db.data.source.name");

            mailAddress = prop.getProperty("mail.address");
            mailPassword = prop.getProperty("mail.password");
            mailHost = prop.getProperty("mail.host");
            mailPort = prop.getProperty("mail.port");
            mailSSL = Boolean.parseBoolean(prop.getProperty("mail.ssl"));
            mailAuth = Boolean.parseBoolean(prop.getProperty("mail.auth"));
            mailAppUrl = prop.getProperty("mail.app.url");
            mailResetUrl = prop.getProperty("mail.reset.url");

        } catch (IOException e) {
            logger.error("Failed to load properties!", e);
        }
    }

    public static boolean isRead() {
        return isRead;
    }

    public static String getDbContextFactory() {
        return dbContextFactory;
    }

    public static String getDbProviderUrl() {
        return dbProviderUrl;
    }

    public static String getDbDataSourceName() {
        return dbDataSourceName;
    }

    public static String getMailAddress() {
        return mailAddress;
    }

    public static String getMailPassword() {
        return mailPassword;
    }

    public static String getMailHost() {
        return mailHost;
    }

    public static String getMailPort() {
        return mailPort;
    }

    public static boolean getMailSSL() {
        return mailSSL;
    }

    public static boolean getMailAuth() {
        return mailAuth;
    }

    public static String getMailAppUrl() {
        return mailAppUrl;
    }

    public static String getMailResetUrl() {
        return mailResetUrl;
    }

}
