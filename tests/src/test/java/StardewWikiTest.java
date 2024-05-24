import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import io.github.cdimascio.dotenv.Dotenv;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class) // Adding test case dependencies
public class StardewWikiTest {
    private WebDriver driver;
    private String username;
    private String pwd;

    @BeforeAll
    public void setup() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();

        // Modifying browser options to accept expired and insecure certificates
        options.setAcceptInsecureCerts(true);

        this.driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        this.driver.manage().window().maximize();

        Dotenv dotenv = Dotenv.load(); // Configuration file
        username = dotenv.get("USERNAME");
        pwd = dotenv.get("PASSWORD");
    }

    @Test
    public void initialSeleniumStaticMainPageTest() {
        MainPage mainPage = new MainPage(this.driver);

        Assertions.assertTrue(mainPage.getFooterText().contains("Content is available under Creative Commons Attribution-NonCommercial-ShareAlike unless otherwise noted."));
        Assertions.assertTrue(mainPage.getBodyText().contains("Stardew Valley is an open-ended country-life RPG!"));
    } 
    
    @Test
    public void pageTitleTest() {
        MainPage mainPage = new MainPage(this.driver);
        String pageTitle = mainPage.getTitle();
        Assertions.assertEquals("Stardew Valley Wiki", pageTitle);
    }

    @Test
    @Order(1)
    public void failedLoginTest() {
        MainPage mainPage = new MainPage(this.driver);
        LoginPage loginPage = mainPage.clickLoginItem();

        Assertions.assertTrue(loginPage.getBodyText().contains("Forgot your password?"));

        loginPage.signIn(username, "WrongPassword");
        Assertions.assertTrue(loginPage.getBodyText().contains("Incorrect username or password entered. Please try again."));
    }

    @Test
    @Order(2)
    public void successfulLoginTest() {
        MainPage mainPage = new MainPage(this.driver);
        LoginPage loginPage = mainPage.clickLoginItem();

        Assertions.assertTrue(loginPage.getBodyText().contains("Forgot your password?"));
        
        loginPage.clickRememberMeCheckBox();
        LoggedInPage loggedInPage = loginPage.signIn(username, pwd);
        Assertions.assertNotNull(loggedInPage);

        Assertions.assertEquals("Danimodesti", loggedInPage.getLoggedUserLink());
    }

    @Test
    @Order(3)
    public void formSendingWithUserTest() {
        LoggedInPage loggedInPage = new LoggedInPage(this.driver);
        PreferencesPage preferencesPage = loggedInPage.goToUserPreferences();

        preferencesPage.fillOutPreferencesForm("Danielle Modesti", "pt-br", "She");
        preferencesPage.savePreferences();
        FrequentChangesPage frequentChangesPage = preferencesPage.goToFrequentChangesPage();
        Assertions.assertTrue(frequentChangesPage.getBodyText().contains("Acompanhe nesta página as mudanças mais recentes deste wiki."));
        Assertions.assertEquals("Danimodesti", frequentChangesPage.getLoggedUser());

        PreferencesPage preferencesPageAgain = frequentChangesPage.getUserPreferences();
        Assertions.assertEquals("Nome de usuária:", preferencesPageAgain.getUsernameLabelText());
        preferencesPageAgain.fillOutLanguage("en");
        preferencesPage.savePreferences();
    }
    
    @Test
    @Order(4)
    public void logoutTest() {
        LoggedInPage loggedInPage = new LoggedInPage(this.driver);
        LoggedOutPage loggedOutPage = loggedInPage.logout();
        
        Assertions.assertTrue(loggedOutPage.getBodyText().contains("You are now logged out."));
    }
    
    @ParameterizedTest
    @CsvSource({
        "chicken, Chickens are colored White, Brown, or Blue. The Blue variety can be obtained only after seeing Shane's 8-heart event. The color of the chicken is assigned randomly by the game when a chicken is purchased.", 
        "cow, A jug of cow's milk.", 
        "bread, A convenient snack for the explorer.", 
        "grandpa, I'd lost sight of what mattered most in life... real connections with other people and nature.", 
        "starfruit, The Starfruit's sprite depicts a cross-sectional slice of a real starfruit, which is oblong in shape."
    })
    public void multiplePageTestFromArray(String searchQuery, String resultQuery) {
        MainPage mainPage = new MainPage(this.driver);
        SearchResultPage searchResultPage = mainPage.search(searchQuery);
        Assertions.assertTrue(searchResultPage.getResultsText().contains(resultQuery));
    }

    @Test
    public void goDeepAndGoBackTest() {
        MainPage mainPage = new MainPage(this.driver);
        SearchResultPage searchResultPage = mainPage.search("farm");
        String resultsForFarm = "You’ve inherited your grandfather’s old farm plot in Stardew Valley.";
        Assertions.assertTrue(searchResultPage.getResultsText().contains(resultsForFarm));
        searchResultPage.clickLinkByText("Marriage");
        String resultsForMarriage = "Romance can only begin once you have reached 8 hearts";
        Assertions.assertTrue(searchResultPage.getResultsText().contains(resultsForMarriage));
        searchResultPage.goBack();
        Assertions.assertTrue(searchResultPage.getResultsText().contains(resultsForFarm));
    }
    
    @AfterAll
    public void close() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }
}