package pages;
import org.openqa.selenium.*;

public class MainPage extends PageBase {
    private By loginLinkLocator = By.xpath("//li[@id='pt-login']//ancestor::a");

    public MainPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://stardewvalleywiki.com/Stardew_Valley_Wiki");
    }

    public String getTitle() {
        return this.driver.getTitle();
    }

    public LoginPage clickLoginItem() {
        waitAndReturnElement(loginLinkLocator).click();
        return new LoginPage(this.driver);
    }
}