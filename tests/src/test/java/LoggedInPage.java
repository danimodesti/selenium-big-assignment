import org.openqa.selenium.*;

public class LoggedInPage extends PageBase {
    private By loggedUserLinkLocator = By.xpath("//a[@href='/User:Danimodesti']");
    private By logoutLinkLocator = By.xpath("//li[@id='pt-logout']//ancestor::a");
    private By preferencesLinkLocator = By.xpath("//a[@href=\"/Special:Preferences\"]");

    public LoggedInPage(WebDriver driver) {
        super(driver);
        waitAndReturnElement(bodyLocator);
    }

    public String getLoggedUserLink() {
        WebElement loggedUserLink = waitAndReturnElement(loggedUserLinkLocator);
        return loggedUserLink.getText();
    }

    public LoggedOutPage logout() {
        waitAndReturnElement(logoutLinkLocator).click();
        return new LoggedOutPage(this.driver);
    }
    
    public PreferencesPage goToUserPreferences() {
        waitAndReturnElement(preferencesLinkLocator).click();
        return new PreferencesPage(this.driver);
    }
}