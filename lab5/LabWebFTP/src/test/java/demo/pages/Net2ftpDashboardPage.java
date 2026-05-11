package demo.pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    public void waitForDashboard() {
        new WebDriverWait(getDriver(), 20)
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//input[@id='smallbutton' and @value='New dir']")));
    }

    public boolean isLoggedIn() {
        return getDriver().getPageSource().contains("Log out") ||
                getDriver().getPageSource().contains("logout");
    }

    private void jsClick(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element);
    }

    public void createFolder(String folderName) {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);

        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        // Re-find each element fresh to avoid stale references
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[@id='smallbutton' and @value='New dir']")));
        jsClick(getDriver().findElement(By.xpath("//input[@id='smallbutton' and @value='New dir']")));

        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("newNames[1]")));
        input.clear();
        input.sendKeys(folderName);

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//img[contains(@src,'button_ok.png')]")));
        jsClick(getDriver().findElement(By.xpath("//img[contains(@src,'button_ok.png')]")));

        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//img[contains(@src,'back.png')]")));
        jsClick(getDriver().findElement(By.xpath("//img[contains(@src,'back.png')]")));

        // Wait for dashboard to reload with updated file listing
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[@id='smallbutton' and @value='New dir']")));
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
    }


    public boolean isFolderVisible(String folderName) {
        return !getDriver().findElements(
                By.xpath("//*[contains(@title,'" + folderName + "') or contains(@value,'" + folderName + "')]"))
                .isEmpty();
    }

    public void deleteFolder(String folderName) {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);

        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[@type='checkbox' and @value='" + folderName + "']")));
        jsClick(getDriver().findElement(By.xpath("//input[@type='checkbox' and @value='" + folderName + "']")));

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[@type='button' and @value='Delete']")));
        jsClick(getDriver().findElement(By.xpath("//input[@type='button' and @value='Delete']")));

        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//img[contains(@src,'button_ok.png')]")));
        jsClick(getDriver().findElement(By.xpath("//img[contains(@src,'button_ok.png')]")));

        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//img[contains(@src,'back.png')]")));
        jsClick(getDriver().findElement(By.xpath("//img[contains(@src,'back.png')]")));

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[@id='smallbutton' and @value='New dir']")));
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
    }

    public boolean isFolderDeleted(String folderName) {
        return getDriver().findElements(
                By.xpath("//*[contains(@title,'" + folderName + "') or contains(@value,'" + folderName + "')]"))
                .isEmpty();
    }

    public boolean isOnLoginPage() {
        // Verifică că după logout suntem din nou pe pagina de login
        return getDriver().getPageSource().contains("FTP server") ||
                getDriver().getCurrentUrl().contains("net2ftp");
    }
}