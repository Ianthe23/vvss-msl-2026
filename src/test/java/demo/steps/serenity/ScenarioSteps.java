package demo.steps.serenity;

import demo.pages.Net2ftpPage;
import demo.pages.Net2ftpDashboardPage;
import net.thucydides.core.annotations.Step;
import org.junit.Assert;

public class ScenarioSteps {

    Net2ftpPage loginPage;
    Net2ftpDashboardPage dashboardPage;

    @Step("Navighez la pagina de login")
    public void navigateToApp() {
        loginPage.open();
    }

    @Step("Mă autentific cu userul {0} și parola {1}")
    public void loginAs(String username, String password) {
        loginPage.enterFtpServer("localhost");
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
    }

    @Step("Verific că sunt autentificat")
    public void verifyIsLoggedIn() {
        Assert.assertTrue("Ar trebui să fiu autentificat după login valid",
                dashboardPage.isLoggedIn());
    }

    @Step("Creez folderul {0}")
    public void createFolder(String folderName) {
        dashboardPage.createFolder(folderName);
    }

    @Step("Verific că folderul {0} există")
    public void verifyFolderExists(String folderName) {
        Assert.assertTrue("Folderul " + folderName + " ar trebui să existe",
                dashboardPage.isFolderVisible(folderName));
    }

    @Step("Șterg folderul {0}")
    public void deleteFolder(String folderName) {
        dashboardPage.deleteFolder(folderName);
    }

    @Step("Verific că folderul {0} a fost șters")
    public void verifyFolderDeleted(String folderName) {
        Assert.assertTrue("Folderul " + folderName + " ar trebui să fi fost șters",
                dashboardPage.isFolderDeleted(folderName));
    }

    @Step("Mă deconectez")
    public void logout() {
        dashboardPage.clickLogout();
    }

    @Step("Verific că sunt deconectat")
    public void verifyIsLoggedOut() {
        Assert.assertTrue("Ar trebui să fiu pe pagina de login după logout",
                dashboardPage.isOnLoginPage());
    }
}