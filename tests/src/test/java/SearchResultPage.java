import org.openqa.selenium.*;

public class SearchResultPage extends PageBase {
    public SearchResultPage(WebDriver driver) {
        super(driver);
        waitAndReturnElement(bodyLocator);
    }

    public void clickByText(String resultName) {
        By locator = By.xpath("//div[@id='0']//span[contains(text(), \"" + resultName + "\")]//ancestor::a");
        
    }
}
