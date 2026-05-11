package demo.steps.serenity;

import demo.pages.Net2ftpPage;
import net.thucydides.core.annotations.Step;
import org.junit.Assert;

public class LoginSteps {

    Net2ftpPage net2ftpPage;

    @Step
    public void navigateToLoginPage() {
        net2ftpPage.openWithAuth();
        net2ftpPage.acceptCookiesAndWaitForLoginForm();
    }

    @Step
    public void enterCredentials(String ftpServer, String username, String password) {
        net2ftpPage.enterFtpServer(ftpServer);
        net2ftpPage.enterUsername(username);
        net2ftpPage.enterPassword(password);
    }

    @Step
    public void clickLogin() {
        net2ftpPage.clickLogin();
    }

    @Step
    public void verifyLoginSuccess() {
        Assert.assertTrue("Login ar trebui să reușească cu date valide",
                net2ftpPage.isLoginSuccessful());
    }

    @Step
    public void verifyLoginFailure() {
        // Wait up to 60s for net2ftp to finish the failed connection attempt
        // The error page renders a "Go to the login page" link or dashboard "New dir" button
        org.openqa.selenium.support.ui.WebDriverWait wait =
            new org.openqa.selenium.support.ui.WebDriverWait(net2ftpPage.getDriver(), 60);
        wait.until(driver ->
            !driver.findElements(org.openqa.selenium.By.xpath(
                "//a[contains(text(),'login page')] | //input[@id='smallbutton' and @value='New dir'] | //input[@name='ftpserver']"
            )).isEmpty()
        );
        Assert.assertFalse("Login ar trebui să eșueze - nu ar trebui să fim pe dashboard",
                net2ftpPage.isLoginSuccessful());
    }
}
