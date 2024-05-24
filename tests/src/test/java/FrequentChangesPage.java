import org.openqa.selenium.*;

public class FrequentChangesPage extends PageBase {
    private By firstHeadingLocator = By.id("firstHeading");
    private By loggedUserLinkLocator = By.xpath("//a[@href='/User:Danimodesti']");
    private By preferencesLinkLocator = By.xpath("//a[@href=\"/Special:Preferences\"]");

    public FrequentChangesPage(WebDriver driver) {
        super(driver);
        waitAndReturnElement(bodyLocator);
    }

    public String getMainTitle() {
        WebElement firstHeading = waitAndReturnElement(firstHeadingLocator);
        return firstHeading.getText();
    }

    public String getLoggedUser() {
        WebElement loggedUserLink = waitAndReturnElement(loggedUserLinkLocator);
        return loggedUserLink.getText();
    }


    public PreferencesPage getUserPreferences() {
        waitAndReturnElement(preferencesLinkLocator).click();
        return new PreferencesPage(this.driver);
    }
}