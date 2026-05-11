package demo.features.search;

import demo.steps.serenity.ScenarioSteps;
import demo.util.AuthProxyManager;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(SerenityRunner.class)
public class ScenarioTest {

    @BeforeClass
    public static void startProxy() {
        AuthProxyManager.startIfNotRunning();
    }

    @Managed(uniqueSession = false)
    public WebDriver webdriver;

    @Steps
    public ScenarioSteps scenarioSteps;

    @Issue("#WIKI-3")
    @Title("Scenariu: login valid, creare folder, stergere folder, logout")
    @Test
    public void scenario_login_create_folder_delete_folder_logout() {
        // Pasul 1: Login valid
        scenarioSteps.navigateToApp();
        scenarioSteps.loginAs("vvta2", "vvta2");
        scenarioSteps.verifyIsLoggedIn();

        // Pasul 2: Creează folder
        String folderName = "TestFolder_" + System.currentTimeMillis();
        scenarioSteps.createFolder(folderName);
        //scenarioSteps.verifyFolderExists(folderName);

        // Pasul 3: Șterge folderul
        scenarioSteps.deleteFolder(folderName);
        scenarioSteps.verifyFolderDeleted(folderName);

        // Pasul 4: Logout
        scenarioSteps.logout();
        scenarioSteps.verifyIsLoggedOut();
    }

    @Issue("#WIKI-4")
    @Title("Scenariu: login, creare folder, logout (fara stergere)")
    @Test
    public void scenario_login_create_folder_logout() {
        scenarioSteps.navigateToApp();
        scenarioSteps.loginAs("vvta3", "vvta3");
        scenarioSteps.verifyIsLoggedIn();

        String folderName = "FolderB_" + System.currentTimeMillis();
        scenarioSteps.createFolder(folderName);
        scenarioSteps.verifyFolderExists(folderName);

        scenarioSteps.logout();
        scenarioSteps.verifyIsLoggedOut();
    }

    @Issue("#WIKI-5")
    @Title("Scenariu: login si logout simplu")
    @Test
    public void scenario_login_and_logout() {
        scenarioSteps.navigateToApp();
        scenarioSteps.loginAs("vvta4", "vvta4");
        scenarioSteps.verifyIsLoggedIn();

        scenarioSteps.logout();
        scenarioSteps.verifyIsLoggedOut();
    }
}