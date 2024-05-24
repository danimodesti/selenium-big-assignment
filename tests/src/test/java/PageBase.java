import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class PageBase {
    protected WebDriver driver;
    protected WebDriverWait wait;

    protected By bodyLocator = By.tagName("body");
    protected By footerLocator = By.tagName("footer");

    public PageBase(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    protected WebElement waitAndReturnElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    }

    public void clickBody() {
        waitAndReturnElement(bodyLocator).click();
    }

    public String getBodyText() {
        WebElement bodyElement = waitAndReturnElement(bodyLocator);
        return bodyElement.getText();
    }

    public String getFooterText() {
        WebElement footer = waitAndReturnElement(footerLocator);
        return footer.getText();
    }

    public void hold() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(bodyLocator));
        } catch (TimeoutException e) {
        }
    }
}
