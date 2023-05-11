package org.simple.blog.record.and.play;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.jupiter.api.Assertions.*;
import static org.simple.blog.record.and.play.TestConstants.*;

public class RecordAndPlayTest {
    private static WebDriver driver;

    @BeforeEach
    public void setUp() {
        setDriver();
        driver.get(BASE_URL);
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

    @Test
    public void profileEdit() {
        loginByTestUser(LOGIN_USER_EMAIL, LOGIN_USER_PASSWORD);
        driver.findElement(By.linkText(USER_F_DEF_NAME + " " + USER_L_DEF_NAME)).click();
        assertEquals(driver.getTitle(), PROFILE_PAGE);

        //set new values
        driver.findElement(By.id("firstName")).clear();
        driver.findElement(By.id("firstName")).sendKeys(USER_F_NEW_NAME);
        driver.findElement(By.id("lastName")).clear();
        driver.findElement(By.id("lastName")).sendKeys(USER_L_NEW_NAME);
        driver.findElement(By.id("birthday")).clear();
        driver.findElement(By.id("birthday")).sendKeys(USER_NEW_AGE);
        driver.findElement(By.cssSelector(".btn:nth-child(5)")).click();

        //check if changed
        assertEquals(driver.getTitle(), PROFILE_PAGE);
        driver.findElement(By.linkText(USER_F_NEW_NAME + " " + USER_L_NEW_NAME)).click();
        assertEquals(driver.getTitle(), PROFILE_PAGE);
        assertEquals(driver.findElement(By.id("firstName")).getAttribute("value"), USER_F_NEW_NAME);
        assertEquals(driver.findElement(By.id("lastName")).getAttribute("value"), USER_L_NEW_NAME);
        assertEquals(driver.findElement(By.id("birthday")).getAttribute("value"), USER_NEW_AGE);

        //return to def values
        driver.findElement(By.id("firstName")).clear();
        driver.findElement(By.id("firstName")).sendKeys(USER_F_DEF_NAME);
        driver.findElement(By.id("lastName")).clear();
        driver.findElement(By.id("lastName")).sendKeys(USER_L_DEF_NAME);
        driver.findElement(By.id("birthday")).clear();
        driver.findElement(By.id("birthday")).sendKeys(USER_DEF_AGE);
        driver.findElement(By.cssSelector(".btn:nth-child(5)")).click();

        logout();
    }

    //run only with working MailHog or SMTP Server
    @Test
    public void forgotPasswordSuccessTest() {
        driver.manage().window().maximize();
        driver.findElement(By.linkText("Forgot password?")).click();
        driver.findElement(By.id("email")).sendKeys(LOGIN_USER_EMAIL);
        driver.findElement(By.cssSelector(".btn:nth-child(2)")).click();

        assertEquals(driver.getTitle(), FORGOT_PAGE);
        assertDoesNotThrow(() -> {
            driver.findElement(By.cssSelector(".alert-success"));
        });

        driver.close();
    }

    @Test
    public void forgotPasswordFailTest() {
        driver.manage().window().maximize();
        driver.findElement(By.linkText("Forgot password?")).click();
        driver.findElement(By.id("email")).sendKeys(LOGIN_USER_WRONG_EMAIL);
        driver.findElement(By.cssSelector(".btn:nth-child(2)")).click();

        assertEquals(driver.getTitle(), FORGOT_PAGE);
        assertDoesNotThrow(() -> {
            driver.findElement(By.cssSelector(".alert-danger"));
        });

        driver.close();
    }

    //driver.findElement(By.linkText("Register")).click();
    //registration-link

    @Test
    public void successRegTest() {
        driver.manage().window().maximize();
        driver.findElement(By.id("registration-link")).click();
        fillRegFieldsWithoutCPassword();
        driver.findElement(By.id("cPassword")).sendKeys(REG_USER_PASSWORD);
        driver.findElement(By.cssSelector(".btn")).click();

        loginByTestUser(REG_USER_EMAIL, REG_USER_PASSWORD);
        assertEquals(driver.getTitle(), HOME_PAGE);
        logout();

        cleanTestData();
    }

    @Test
    public void failedRegTest() {
        driver.manage().window().maximize();
        driver.findElement(By.id("registration-link")).click();
        fillRegFieldsWithoutCPassword();
        driver.findElement(By.cssSelector(".btn")).click();
        assertEquals(driver.getTitle(), REG_PAGE);
        driver.close();
    }

    private void fillRegFieldsWithoutCPassword() {
        driver.findElement(By.id("firstName")).sendKeys(REG_FIRST_NAME);
        driver.findElement(By.id("lastName")).sendKeys(REG_LAST_NAME);
        driver.findElement(By.id("email")).sendKeys(REG_USER_EMAIL);
        driver.findElement(By.id("birthday")).sendKeys(REG_AGE);
        driver.findElement(By.id("password")).sendKeys(REG_USER_PASSWORD);
    }

    @Test
    public void logoutTest() {
        loginByTestUser(LOGIN_USER_EMAIL, LOGIN_USER_PASSWORD);
        assertEquals(driver.getTitle(), HOME_PAGE);
        driver.findElement(By.linkText("Logout")).click();
        assertEquals(driver.getTitle(), LOGIN_PAGE);
        driver.close();;
    }

    @Test
    public void emptyPasswordLoginTest() {
        loginByTestUser(LOGIN_USER_EMAIL, "");
        assertEquals(driver.getTitle(), LOGIN_PAGE);
        driver.close();;
    }

    @Test
    public void emptyEmailLoginTest() {
        loginByTestUser("", LOGIN_USER_PASSWORD);
        assertEquals(driver.getTitle(), LOGIN_PAGE);
        driver.close();;
    }

    @Test
    public void wrongPasswordLoginTest() {
        loginByTestUser(LOGIN_USER_EMAIL, LOGIN_USER_WRONG_PASSWORD);
        assertEquals(driver.getTitle(), LOGIN_PAGE);
        assertDoesNotThrow(() -> {
            driver.findElement(By.cssSelector(".alert-danger"));
        });
        driver.close();;
    }

    @Test
    public void wrongEmailLoginTest() {
        loginByTestUser(LOGIN_USER_WRONG_EMAIL, LOGIN_USER_PASSWORD);
        assertEquals(driver.getTitle(), LOGIN_PAGE);
        assertDoesNotThrow(() -> {
            driver.findElement(By.cssSelector(".alert-danger"));
        });
        driver.close();;
    }

    @Test
    public void successLoginTest() {
        loginByTestUser(LOGIN_USER_EMAIL, LOGIN_USER_PASSWORD);
        assertEquals(driver.getTitle(), HOME_PAGE);
        logout();
    }

    @Test
    public void createPostTest() {
        loginByTestUser(LOGIN_USER_EMAIL, LOGIN_USER_PASSWORD);
        createPost();
        assertDoesNotThrow(() -> {
            driver.findElement(By.xpath("//*[text()='" + POST_NAME + "']"));
        });
        logout();
        //delete post
        cleanTestData();
    }

    @Test
    public void createCommentTest() {
        loginByTestUser(LOGIN_USER_EMAIL, LOGIN_USER_PASSWORD);
        createPost();
        driver.findElement(By.linkText("Show more...")).click();
        createComment();
        assertDoesNotThrow(() -> {
            driver.findElement(By.xpath("//*[text()='" + COMMENT_TEXT + "']"));
        });
        logout();
        //delete post and comment
        cleanTestData();
    }

    private void createComment() {
        driver.findElement(By.id("commentText")).sendKeys(COMMENT_TEXT);
        driver.findElement(By.cssSelector(".btn:nth-child(2)")).click();
    }

    private void createPost() {
        driver.findElement(By.linkText(CREATE_POST_BUTTON)).click();
        driver.findElement(By.id("postName")).sendKeys(POST_NAME);
        driver.findElement(By.id("postText")).sendKeys(POST_TEXT);
        driver.findElement(By.cssSelector(".btn:nth-child(3)")).click();
    }

    private void loginByTestUser(String email, String password) {
        driver.manage().window().maximize();
        driver.findElement(By.id("loginUserName")).sendKeys(email);
        driver.findElement(By.id("loginPassword")).sendKeys(password);
        driver.findElement(By.cssSelector(".btn")).click();
    }

    private void logout() {
        driver.findElement(By.linkText("Logout")).click();
        driver.close();
    }
}