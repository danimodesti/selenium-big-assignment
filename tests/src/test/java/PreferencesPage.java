import org.openqa.selenium.*;

import java.util.HashMap;
import java.util.Map;

public class PreferencesPage extends PageBase {
    private By firstHeadingLocator = By.xpath("//h1[@id='firstHeading']");
    private By realNameInputLocator = By.xpath("//input[@id='ooui-php-6']");
    private String languageComboBoxId = "ooui-php-19";
    private By savePreferencesButtonLocator = By.xpath("//button//ancestor::span[@class='oo-ui-labelElement-label']");
    private By frequentChangesLinkLocator = By.xpath("//li[@id='n-recentchanges']//ancestor::a");
    private By usernameLabelLocator = By.id("ooui-php-2");

    public PreferencesPage(WebDriver driver) {
        super(driver);
        waitAndReturnElement(firstHeadingLocator);
    }

    public String getUsernameLabelText() {
        WebElement usernameLabel = waitAndReturnElement(usernameLabelLocator);
        return usernameLabel.getText();
    }

    public void fillOutRealName(String realName) {
        WebElement realNameInput = waitAndReturnElement(realNameInputLocator);
        realNameInput.clear();
        realNameInput.sendKeys(realName);
    }

    public void fillOutLanguage(String language) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
            "var languageComboBox = document.getElementById('" + languageComboBoxId + "');" +
            "languageComboBox.value = '" + language + "';" +
            "var event = new Event('change');" +
            "languageComboBox.dispatchEvent(event);"
        );
    }

    public void fillOutPronoun(String pronoun) {
        Map<String, String> pronounToIdMap = new HashMap<>();
        pronounToIdMap.put("He", "ooui-php-22");
        pronounToIdMap.put("She", "ooui-php-21");
        pronounToIdMap.put("They", "ooui-php-20");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
            "var pronounRadioButton = document.getElementById('" + pronounToIdMap.get(pronoun) + "');" +
            "pronounRadioButton.click();"
        );
    }

    public void savePreferences() {
        WebElement savePreferencesButton = waitAndReturnElement(savePreferencesButtonLocator);
        savePreferencesButton.click();
    }

    public void fillOutPreferencesForm(String realName, String language, String pronoun) {
        fillOutRealName(realName);

        this.clickBody();

        fillOutLanguage(language);        
    }

    public FrequentChangesPage checkFrequentChanges() {
        waitAndReturnElement(frequentChangesLinkLocator).click();

        return new FrequentChangesPage(this.driver);
    }
}
