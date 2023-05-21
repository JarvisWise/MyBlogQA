package org.simple.blog.selenium.web.driver.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.simple.blog.record.and.play.TestConstants.*;
import static org.simple.blog.record.and.play.TestConstants.USER_NEW_AGE;

public class BrowserProfilePage {
    WebDriver driver;
    By firstName = By.id("firstName");
    By lastName = By.id("lastName");
    By birthday = By.id("birthday");
    By profileUpdateButton = By.cssSelector(".btn:nth-child(5)");

    public BrowserProfilePage(WebDriver webDriver) {
        this.driver = webDriver;
    }

    public void isProfilePage() {
        assertEquals(driver.getTitle(), PROFILE_PAGE);
    }

    //USER_F_NEW_NAME USER_L_NEW_NAME USER_NEW_AGE
    public void fillUserInfo(String fName, String lName, String age) {
        driver.findElement(firstName).clear();
        driver.findElement(firstName).sendKeys(fName);
        driver.findElement(lastName).clear();
        driver.findElement(lastName).sendKeys(lName);
        driver.findElement(birthday).clear();
        driver.findElement(birthday).sendKeys(age);
    }

    public void clickProfileUpdate() {
        driver.findElement(profileUpdateButton).click();
    }

    //USER_F_NEW_NAME USER_L_NEW_NAME USER_NEW_AGE
    public void verifyFieldsIsChanged(String fName, String lName, String age) {
        assertEquals(driver.findElement(firstName).getAttribute("value"), fName);
        assertEquals(driver.findElement(lastName).getAttribute("value"), lName);
        assertEquals(driver.findElement(birthday).getAttribute("value"), age);
    }
}
