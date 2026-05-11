package demo.pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Net2ftpDashboardPage extends PageObject {

    // IMPORTANT: Ajustează selectoarele după ce inspectezi pagina cu DevTools (F12)

    @FindBy(xpath = "//img[contains(@src,'exit.png')]")
    private WebElement logoutButton;

    @FindBy(xpath = "//img[contains(@src,'back.png')]")
    private WebElement backButton;

    @FindBy(id = "smallbutton")
    private WebElement newDirButton;

    @FindBy(name = "newNames[1]")
    private WebElement newFolderInput;

    @FindBy(xpath = "//img[contains(@src,'button_ok.png')]")
    private WebElement submitCreateFolderButton;

    // Câmpul pentru introducerea numelui noului folder
    // (inspectează pagina și găsește selectorul corect)
    @FindBy(name = "newfoldername")
    private WebElement newFolderNameField;

    // Butonul de creare folder
    @FindBy(css = "input[value='Create folder']")
    private WebElement createFolderButton;

    public void clickLogout() {
        logoutButton.click();
    }

    public boolean isLoggedIn() {
        // Verifică că suntem pe pagina de după autentificare
        return getDriver().getPageSource().contains("Log out") ||
                getDriver().getPageSource().contains("logout");
    }

    public void createFolder(String folderName) {

        newDirButton.click();

        newFolderInput.clear();
        newFolderInput.sendKeys(folderName);

        submitCreateFolderButton.click();

        backButton.click();
    }


    public boolean isFolderVisible(String folderName) {
        // Verifică că folderul apare în lista de fișiere
        return getDriver().getPageSource().contains(folderName);
    }

    public void deleteFolder(String folderName) {

        WebElement folderCheckbox = getDriver().findElement(
                By.xpath("//input[@type='checkbox' and @value='" + folderName + "']")
        );
        evaluateJavascript("arguments[0].click();", folderCheckbox);

        WebElement deleteButton = getDriver().findElement(
                By.xpath("//input[@type='button' and @value='Delete']")
        );
        evaluateJavascript("arguments[0].click();", deleteButton);

        WebElement submitDeleteButton = getDriver().findElement(
                By.xpath("//img[contains(@src,'button_ok.png')]")
        );
        evaluateJavascript("arguments[0].click();", submitDeleteButton);

        WebElement backButton = getDriver().findElement(
                By.xpath("//img[contains(@src,'back.png')]")
        );
        evaluateJavascript("arguments[0].click();", backButton);
    }

    public boolean isFolderDeleted(String folderName) {
        return !getDriver().getPageSource().contains(folderName);
    }

    public boolean isOnLoginPage() {
        // Verifică că după logout suntem din nou pe pagina de login
        return getDriver().getPageSource().contains("FTP server") ||
                getDriver().getCurrentUrl().contains("net2ftp");
    }
}