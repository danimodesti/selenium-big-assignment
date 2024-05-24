package pages;
import org.openqa.selenium.*;

public class SearchResultPage extends PageBase {
    private By bodyContentLocator = By.id("bodyContent");

    public SearchResultPage(WebDriver driver) {
        super(driver);
        waitAndReturnElement(bodyLocator);
    }

    public String getResultsText() {
        WebElement bodyContent = waitAndReturnElement(bodyContentLocator);
        return bodyContent.getText();
    }
}