import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;

import java.net.MalformedURLException;

import org.junit.*;

import io.github.cdimascio.dotenv.Dotenv;

public class StardewWikiTest {
    private WebDriver driver;
    private String username;
    private String pwd;

    @Before
    public void setup() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        this.driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        this.driver.manage().window().maximize();

        Dotenv dotenv = Dotenv.load();
        username = dotenv.get("USERNAME");
        pwd = dotenv.get("PASSWORD");
    }

    
    @Test
    public void initialSeleniumStaticMainPageTest() {
        MainPage mainPage = new MainPage(this.driver);
        System.out.println(mainPage.getBodyText());

        Assert.assertTrue(mainPage.getFooterText().contains("Content is available under Creative Commons Attribution-NonCommercial-ShareAlike unless otherwise noted."));
        Assert.assertTrue(mainPage.getBodyText().contains("Stardew Valley is an open-ended country-life RPG!"));
    } 

    
    @Test
    public void pageTitleTest() {
        MainPage mainPage = new MainPage(this.driver);
        String pageTitle = mainPage.getTitle();
        System.out.println("Page title: " + pageTitle);
        Assert.assertEquals("Stardew Valley Wiki", pageTitle);
    }

    @Test
    public void loginLogoutTest() {
        MainPage mainPage = new MainPage(this.driver);
        LoginPage loginPage = mainPage.clickLoginItem();

        Assert.assertTrue(loginPage.getBodyText().contains("Forgot your password?"));

        loginPage.clickRememberMeCheckBox();
        LoggedInPage loggedInPage = loginPage.signIn(username, pwd);
        Assert.assertNotNull(loggedInPage);

        Assert.assertEquals("Danimodesti", loggedInPage.getLoggedUserLink());

        LoggedOutPage loggedOutPage = loggedInPage.logout();

        Assert.assertTrue(loggedOutPage.getBodyText().contains("You are now logged out."));
    } 
    
    
    @Test
    public void formSendingWithUserTest() {
        MainPage mainPage = new MainPage(this.driver);
        LoginPage loginPage = mainPage.clickLoginItem();
        LoggedInPage loggedInPage = loginPage.signIn(username, pwd);
        PreferencesPage preferencesPage = loggedInPage.getUserPreferences();

        preferencesPage.fillOutPreferencesForm("Danielle Modesti", "pt-br", "She");
        preferencesPage.savePreferences();
        FrequentChangesPage frequentChangesPage = preferencesPage.checkFrequentChanges();
        Assert.assertTrue(frequentChangesPage.getBodyText().contains("Acompanhe nesta página as mudanças mais recentes deste wiki."));
        Assert.assertEquals("Danimodesti", frequentChangesPage.getLoggedUserLink());

        PreferencesPage preferencesPageAgain = frequentChangesPage.getUserPreferences();
        Assert.assertEquals("Nome de usuária:", preferencesPageAgain.getUsernameLabelText());
        preferencesPageAgain.fillOutLanguage("en");
        preferencesPage.savePreferences();
    }

    @After
    public void close() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }
}
