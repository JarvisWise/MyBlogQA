package org.simple.blog.selenium.web.driver;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.simple.blog.controllers.ShowController;
import org.simple.blog.selenium.web.driver.pages.*;

import java.io.File;
import java.io.IOException;

import static org.simple.blog.record.and.play.TestConstants.*;


public class SeleniumPageObjectTest {
    private static WebDriver driver;
    private BrowserLoginPage loginPage;
    private BrowserHomePage homePage;
    private BrowserPostPage postPage;
    private BrowserProfilePage profilePage;
    private BrowserRegistrationPage registrationPage;
    private BrowserForgotPasswordPage forgotPasswordPage;
    private static final Logger logger = Logger.getLogger(ShowController.class);

    @BeforeEach
    public void setUp() {
        setDriver();
        driver.get(BASE_URL);

        loginPage = new BrowserLoginPage(driver);
        homePage = new BrowserHomePage(driver);
        postPage = new BrowserPostPage(driver);
        profilePage = new BrowserProfilePage(driver);
        registrationPage = new BrowserRegistrationPage(driver);
        forgotPasswordPage = new BrowserForgotPasswordPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    public void setDriver() {
        System.setProperty(WEB_DRIVER, PATH_TO_DRIVER);
        driver = new ChromeDriver();
    }

    public void cleanTestData() {
        driver.quit();
        setDriver();
        driver.get(CLEAN_URL);
        driver.close();
    }

    public void doScreenShot(String screenName) {
        try {
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(".\\seleniumScreens\\" + screenName + ".png"));
        } catch (IOException e) {
            logger.warn("unable to save screenshots", e);
        }

    }

    @Test
    public void profileEdit() {
        loginPage.fillUserInfo(LOGIN_USER_EMAIL, LOGIN_USER_PASSWORD);
        doScreenShot("editProfile\\login");
        loginPage.clickLoginButton();
        doScreenShot("editProfile\\home");
        homePage.clickProfileLink(USER_F_DEF_NAME, USER_L_DEF_NAME);
        profilePage.isProfilePage();
        doScreenShot("editProfile\\profileBefore");
        //set new values
        profilePage.fillUserInfo(USER_F_NEW_NAME, USER_L_NEW_NAME, USER_NEW_AGE);
        profilePage.clickProfileUpdate();
        //check if changed
        profilePage.isProfilePage();
        homePage.clickProfileLink(USER_F_NEW_NAME, USER_L_NEW_NAME);
        profilePage.isProfilePage();
        doScreenShot("editProfile\\profileAfter");
        profilePage.verifyFieldsIsChanged(USER_F_NEW_NAME, USER_L_NEW_NAME, USER_NEW_AGE);
        //return to def values
        profilePage.fillUserInfo(USER_F_DEF_NAME, USER_L_DEF_NAME, USER_DEF_AGE);
        profilePage.clickProfileUpdate();
        homePage.clickLogoutButton();
        driver.close();
    }

    //run only with working MailHog or SMTP Server
    @Test
    public void forgotPasswordSuccessTest() {
        driver.manage().window().maximize();
        loginPage.clickForgotPasswordButton();
        forgotPasswordPage.fillEmail(LOGIN_USER_EMAIL);
        forgotPasswordPage.clickResetButton();
        forgotPasswordPage.isForgotPasswordPage();
        forgotPasswordPage.isAlertSuccessPresent();
        driver.close();
    }

    @Test
    public void forgotPasswordFailTest() {
        driver.manage().window().maximize();
        loginPage.clickForgotPasswordButton();
        forgotPasswordPage.fillEmail(LOGIN_USER_WRONG_EMAIL);
        forgotPasswordPage.clickResetButton();
        forgotPasswordPage.isForgotPasswordPage();
        forgotPasswordPage.isAlertDangerPresent();
        driver.close();
    }

    @Test
    public void successRegTest() {
        driver.manage().window().maximize();

        driver.manage().window().maximize();
        loginPage.clickRegistrationButton();
        registrationPage.fillRegFieldsWithoutCPassword(
                REG_FIRST_NAME, REG_LAST_NAME, REG_USER_EMAIL, REG_AGE, REG_USER_PASSWORD
        );
        registrationPage.fillConfirmPassword(REG_USER_PASSWORD);
        registrationPage.clickRegistrationButton();
        loginPage.isLoginPage();
        loginPage.fillUserInfo(REG_USER_EMAIL, REG_USER_PASSWORD);
        loginPage.clickLoginButton();
        homePage.isHomePage();
        homePage.clickLogoutButton();
        driver.close();
        cleanTestData();
    }

    @Test
    public void failedRegTest() {
        driver.manage().window().maximize();
        loginPage.clickRegistrationButton();
        registrationPage.fillRegFieldsWithoutCPassword(
                REG_FIRST_NAME, REG_LAST_NAME, REG_USER_EMAIL, REG_AGE, REG_USER_PASSWORD
        );
        registrationPage.clickRegistrationButton();
        registrationPage.isRegistrationPage();
        driver.close();
    }

    @Test
    public void logoutTest() {
        loginPage.fillUserInfo(LOGIN_USER_EMAIL, LOGIN_USER_PASSWORD);
        loginPage.clickLoginButton();
        homePage.isHomePage();
        homePage.clickLogoutButton();
        loginPage.isLoginPage();
        driver.close();
    }

    @Test
    public void emptyPasswordLoginTest() {
        loginPage.fillUserInfo(LOGIN_USER_EMAIL, "");
        loginPage.clickLoginButton();
        loginPage.isLoginPage();
        driver.close();
    }

    @Test
    public void emptyEmailLoginTest() {
        loginPage.fillUserInfo("", LOGIN_USER_PASSWORD);
        loginPage.clickLoginButton();
        loginPage.isLoginPage();
        driver.close();
    }

    @Test
    public void wrongPasswordLoginTest() {
        loginPage.fillUserInfo(LOGIN_USER_EMAIL, LOGIN_USER_WRONG_PASSWORD);
        loginPage.clickLoginButton();
        loginPage.isLoginPage();
        loginPage.isDangerAlertPresent();
        driver.close();
    }

    @Test
    public void wrongEmailLoginTest() {
        loginPage.fillUserInfo(LOGIN_USER_WRONG_EMAIL, LOGIN_USER_PASSWORD);
        loginPage.clickLoginButton();
        loginPage.isLoginPage();
        loginPage.isDangerAlertPresent();
        driver.close();
    }

    @Test
    public void successLoginTest() {
        loginPage.fillUserInfo(LOGIN_USER_EMAIL, LOGIN_USER_PASSWORD);
        loginPage.clickLoginButton();
        homePage.isHomePage();
        homePage.clickLogoutButton();
        driver.close();
    }

    @Test
    public void createPostTest() {
        loginPage.fillUserInfo(LOGIN_USER_EMAIL, LOGIN_USER_PASSWORD);
        loginPage.clickLoginButton();
        homePage.createPost(POST_NAME, POST_TEXT);
        homePage.verifyPostCreated();
        homePage.clickLogoutButton();
        driver.close();
        cleanTestData();
    }

    @Test
    public void createCommentTest() {
        loginPage.fillUserInfo(LOGIN_USER_EMAIL, LOGIN_USER_PASSWORD);
        loginPage.clickLoginButton();
        homePage.createPost(POST_NAME, POST_TEXT);
        homePage.clickShowMore();
        postPage.createComment(COMMENT_TEXT);
        postPage.verifyCommentCreated();
        homePage.clickLogoutButton();
        driver.close();
        cleanTestData();
    }
}
