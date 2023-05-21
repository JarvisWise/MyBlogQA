package org.simple.blog.selenium.web.driver.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.simple.blog.record.and.play.TestConstants.COMMENT_TEXT;

public class BrowserPostPage {
    WebDriver driver;
    By commentText = By.id("commentText");
    By createCommentButton = By.cssSelector(".btn:nth-child(2)");
    By newComment = By.xpath("//*[text()='" + COMMENT_TEXT + "']");

    public BrowserPostPage(WebDriver webDriver) {
        this.driver = webDriver;
    }

    public void createComment(String comment) {
        driver.findElement(commentText).sendKeys(comment);
        driver.findElement(createCommentButton).click();
    }

    public void verifyCommentCreated() {
        assertDoesNotThrow(() -> {
            driver.findElement(newComment);
        });
    }
}
