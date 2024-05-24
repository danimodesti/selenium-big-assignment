import org.openqa.selenium.*;

public class LoginPage extends PageBase {
    private By usernameBarLocator = By.id("wpName1");
    private By passwordBarLocator = By.id("wpPassword1");
    private By loginButtonLocator = By.id("wpLoginAttempt");

    public LoginPage(WebDriver driver) {
        super(driver);
        waitAndReturnElement(bodyLocator);
    }

    public void clickRememberMeCheckBox() {
        ((JavascriptExecutor) driver).executeScript("document.getElementById('wpRemember').click();");
    }

    public LoggedInPage signIn(String username, String pwd) {
        WebElement usernameBar = waitAndReturnElement(usernameBarLocator);
        usernameBar.sendKeys(username);

        WebElement passwordBar = waitAndReturnElement(passwordBarLocator);
        passwordBar.sendKeys(pwd);

        WebElement loginButton = waitAndReturnElement(loginButtonLocator);
        loginButton.click();
                
        return new LoggedInPage(this.driver);
    }
}