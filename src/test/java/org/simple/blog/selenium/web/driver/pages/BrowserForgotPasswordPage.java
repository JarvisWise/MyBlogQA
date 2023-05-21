package org.simple.blog.selenium.web.driver.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.simple.blog.record.and.play.TestConstants.*;

public class BrowserForgotPasswordPage {
    WebDriver driver;
    By email = By.id("email");
    By resetButton = By.cssSelector(".btn:nth-child(2)");
    By alertDanger = By.cssSelector(".alert-danger");
    By alertSuccess = By.cssSelector(".alert-success");

    public BrowserForgotPasswordPage(WebDriver webDriver) {
        this.driver = webDriver;
    }

    public void fillEmail(String fEmail) {
        driver.findElement(email).sendKeys(fEmail);
    }

    public void clickResetButton() {
        driver.findElement(resetButton).click();
    }

    public void isForgotPasswordPage() {
        assertEquals(driver.getTitle(), FORGOT_PAGE);
    }

    public void isAlertDangerPresent() {
        assertDoesNotThrow(() -> {
            driver.findElement(alertDanger);
        });
    }

    public void isAlertSuccessPresent() {
        assertDoesNotThrow(() -> {
            driver.findElement(alertSuccess);
        });
    }
}
