package org.simple.blog.selenium.web.driver.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.simple.blog.record.and.play.TestConstants.*;

public class BrowserHomePage {
    WebDriver driver;
    By logoutButton = By.linkText("Logout");
    By postName = By.id("postName");
    By postText = By.id("postText");
    By createPostButton = By.cssSelector(".btn:nth-child(3)");
    By createPostLink = By.linkText(CREATE_POST_BUTTON);
    By showMoreButton = By.linkText("Show more...");
    By newPost = By.xpath("//*[text()='" + POST_NAME + "']");

    public BrowserHomePage(WebDriver webDriver) {
        this.driver = webDriver;
    }

    public void clickLogoutButton() {
        driver.findElement(logoutButton).click();
    }

    //POST_NAME POST_TEXT
    public void createPost(String name, String text) {
        driver.findElement(createPostLink).click();
        driver.findElement(postName).sendKeys(name);
        driver.findElement(postText).sendKeys(text);
        driver.findElement(createPostButton).click();
    }

    public void clickShowMore() {
        driver.findElement(showMoreButton).click();
    }

    public void verifyPostCreated() {
        assertDoesNotThrow(() -> {
            driver.findElement(newPost);
        });
    }

    public void isHomePage() {
        assertEquals(driver.getTitle(), HOME_PAGE);
    }

    public void clickProfileLink(String fName, String lName) {
        driver.findElement(By.linkText( fName + " " + lName)).click();
    }
}
