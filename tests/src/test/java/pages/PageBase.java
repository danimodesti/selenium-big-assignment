package pages;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class PageBase {
    protected WebDriver driver;
    protected WebDriverWait wait;

    protected By bodyLocator = By.tagName("body");
    protected By footerLocator = By.tagName("footer");
    
    private By searchBarLocator = By.id("searchInput");
    private By suggestionsLocator = By.className("mw-searchSuggest-link");

    public PageBase(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    protected WebElement waitAndReturnElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    }

    protected void waitForElements(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public String getBodyText() {
        WebElement bodyElement = waitAndReturnElement(bodyLocator);
        return bodyElement.getText();
    }

    public String getFooterText() {
        WebElement footer = waitAndReturnElement(footerLocator);
        return footer.getText();
    }

    public SearchResultPage search(String keys) {
        WebElement searchBar = waitAndReturnElement(searchBarLocator);
        searchBar.sendKeys(keys);
        waitForElements(suggestionsLocator);
        searchBar.sendKeys("\n");

        return new SearchResultPage(this.driver);
    }

    public void goBack() {
        driver.navigate().back();
    }

    public SearchResultPage clickLinkByText(String linkText) {
        waitAndReturnElement(By.xpath("//a[contains(text(), '" + linkText + "')]")).click();
        return new SearchResultPage(this.driver);
    }
}