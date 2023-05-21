package org.simple.blog.selenium.web.driver.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.simple.blog.record.and.play.TestConstants.LOGIN_PAGE;

public class BrowserLoginPage {
    WebDriver driver;
    By loginUserName = By.id("loginUserName");
    By loginPassword = By.id("loginPassword");
    By loginButton = By.cssSelector(".btn");
    By dangerAlert = By.cssSelector(".alert-danger");
    By registrationLink = By.id("registration-link");
    By forgotPasswordLink = By.linkText("Forgot password?");

    public BrowserLoginPage(WebDriver webDriver) {
        this.driver = webDriver;
    }

    public void clickRegistrationButton() {
        driver.findElement(registrationLink).click();
    }

    public void fillUserInfo(String email, String password) {
        driver.manage().window().maximize();
        driver.findElement(loginUserName).sendKeys(email);
        driver.findElement(loginPassword).sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public void isLoginPage() {
        assertEquals(driver.getTitle(), LOGIN_PAGE);
    }

    public void isDangerAlertPresent() {
        assertDoesNotThrow(() -> {
            driver.findElement(dangerAlert);
        });
    }

    public void clickForgotPasswordButton() {
        driver.findElement(forgotPasswordLink).click();
    }

}
