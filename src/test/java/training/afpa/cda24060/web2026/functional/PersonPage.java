package training.afpa.cda24060.web2026.functional;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PersonPage {

    private final WebDriverWait wait;
    private final WebDriver driver;

    public PersonPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Éléments de la page home
    public WebElement getAddPersonButton() {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("h4 a[href='/createperson']")));
        } catch (Exception e) {
            throw new RuntimeException("Le bouton 'Ajouter une personne' n'a pas été trouvé.", e);
        }
    }

    public WebElement getFirstPersonInTable() {
        scollPage();
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table tbody tr:last-child")));
    }

    // Éléments de la page createperson
    public WebElement getFirstNameField() {
        scollPage();
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.id("firstNameInput")));
    }

    public WebElement getLastNameField() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.id("lastNameInput")));
    }

    public WebElement getSubmitButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
    }

    // Éléments de la page updateperson
    public WebElement getUpdateFirstNameField() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.id("firstNameInput")));
    }

    public WebElement getUpdateLastNameField() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.id("lastNameInput")));
    }

    public WebElement getUpdateSubmitButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
    }

    // Actions
    public void clickAddPerson() {
        WebElement addButton =  getAddPersonButton();

        scollPage();

        // Attendre un court instant pour que le scroll soit effectif
        // (optionnel, mais aide à la stabilité)
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
        addButton.click();
        wait.until(ExpectedConditions.urlContains("/createperson"));
    }

    public void clickEditFirstPerson() {
        getFirstPersonInTable().findElement(By.cssSelector("a[href*='updateperson']")).click();
    }

    public void clickDeleteFirstPerson() {
        getFirstPersonInTable().findElement(By.cssSelector("a[href*='deleteperson']")).click();
    }

    private void scollPage() {
        // Ajout : Scroll vers l'élément pour le rendre visible et cliquable
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (int i = 0; i < 5; i++) { // Défile 5 fois
            js.executeScript("window.scrollBy(0, 500);"); // Défile de 500 pixels
            try {
                Thread.sleep(500); // Pause de 500ms entre chaque défilement
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
