package pages;
import org.openqa.selenium.*;

public class LoggedOutPage extends PageBase {
    private By firstHeadingLocator = By.xpath("//h1[contains(text(), 'Log out')]");

    public LoggedOutPage(WebDriver driver) {
        super(driver);
        waitAndReturnElement(firstHeadingLocator);
    }
}