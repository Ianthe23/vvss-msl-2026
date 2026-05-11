package demo.pages;

import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@DefaultUrl("https://scs.ubbcluj.ro/vvta/net2ftp/index.php")
public class Net2ftpPage extends PageObject {

    @FindBy(name = "ftpserver")
    private WebElement ftpServerField;

    @FindBy(name = "username")
    private WebElement usernameField;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(name = "Login")
    private WebElement loginButton;

    public void openWithAuth() {
        open();
        // Handle HTTP Basic Auth dialog if it appears
        try {
            Alert alert = new WebDriverWait(getDriver(), 5)
                .until(ExpectedConditions.alertIsPresent());
            alert.sendKeys("vvss\nstrugure");
            alert.accept();
        } catch (Exception ignored) {}
    }

    public void acceptCookiesAndWaitForLoginForm() {
        WebDriverWait shortWait = new WebDriverWait(getDriver(), 5);
        WebDriverWait longWait = new WebDriverWait(getDriver(), 20);

        // Click "Save cookie choice" if cookie consent popup appears
        try {
            WebElement saveBtn = shortWait.until(
                ExpectedConditions.presenceOfElementLocated(By.name("Save")));
            ((org.openqa.selenium.JavascriptExecutor) getDriver())
                .executeScript("arguments[0].click();", saveBtn);
        } catch (Exception ignored) {}

        // Persist consent cookies for future navigations
        try {
            getDriver().manage().addCookie(new Cookie("consent_necessary", "1", "scs.ubbcluj.ro", "/", null));
            getDriver().manage().addCookie(new Cookie("consent_preferences", "1", "scs.ubbcluj.ro", "/", null));
            getDriver().manage().addCookie(new Cookie("consent_statistics", "1", "scs.ubbcluj.ro", "/", null));
            getDriver().manage().addCookie(new Cookie("consent_nonpersonalized_ads", "1", "scs.ubbcluj.ro", "/", null));
        } catch (Exception ignored) {}

        // Wait for FTP login form
        longWait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ftpserver")));
    }

    public void enterFtpServer(String server) {
        ftpServerField.clear();
        ftpServerField.sendKeys(server);
    }

    public void enterUsername(String username) {
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLogin() {
        loginButton.click();
    }

    public boolean isLoginSuccessful() {
        try {
            // Only the dashboard has the "New dir" button - unique to logged-in state
            return !getDriver().findElements(By.xpath("//input[@id='smallbutton' and @value='New dir']")).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLoginFailed() {
        // Login failed = we are NOT on dashboard (no exit.png / New dir button)
        // and we are still on net2ftp (url contains net2ftp)
        return !isLoginSuccessful() && getDriver().getCurrentUrl().contains("net2ftp");
    }
}