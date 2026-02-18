package training.afpa.cda24060.web2026.functional;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;
import java.util.Objects;

@Feature("Tests création, modification et suppression d'une personne")
public class PersonControllerE2ETest extends BaseE2ETest {

    private PersonPage personPage;

    @BeforeMethod
    public void init() {
        personPage = new PersonPage(driver);
    }

    @Test
    @Description("Création d'une nouvelle personne")
    @Story("Création d'une nouvelle personne")
    @Order(1)
    public void testAddPerson() {
        // Constantes pour les valeurs de test
        final String FIRST_NAME = "Pierre";
        final String LAST_NAME = "Martin";

        // 1. Charger la page d'accueil
        driver.get("http://localhost:9001/");
        personPage = new PersonPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 2. Attendre que le lien soit présent
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("h4 a[href='/createperson']")  // Aligné sur PersonPage pour plus de fiabilité
        ));

        // 3. Cliquer sur "Ajouter une personne"
        personPage.clickAddPerson();

        // 4. Attendre que la page de création soit chargée
        wait.until(ExpectedConditions.urlContains("/createperson"));

        // 5. Remplir le formulaire
        personPage.getFirstNameField().sendKeys(FIRST_NAME);
        personPage.getLastNameField().sendKeys(LAST_NAME);
        personPage.getSubmitButton().click();

        // 6. Vérifier que la personne a été ajoutée
        wait.until(ExpectedConditions.urlContains("/"));
        WebElement tableRowFirstname = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//table//*[contains(text(), '" + FIRST_NAME + "')]")));
        WebElement tableRowLastname = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//table//*[contains(text(), '" + LAST_NAME.toUpperCase() + "')]")));

        Assertions.assertTrue(tableRowFirstname.getText().contains(FIRST_NAME));
        Assertions.assertTrue(tableRowLastname.getText().contains(LAST_NAME.toUpperCase()));
    }

    @Test(dependsOnMethods = "testAddPerson")
    @Description("Edition d'une personne")
    @Story("Edition d'une personne")
    @Order(2)
    public void testEditPerson() {

        driver.get("http://localhost:9001/");  // Ajout : Charger la page d'accueil
        personPage = new PersonPage(driver);

        personPage.clickEditFirstPerson();
        personPage.getUpdateFirstNameField().clear();
        personPage.getUpdateFirstNameField().sendKeys("Paul");
        personPage.getUpdateSubmitButton().click();
        Assertions.assertTrue(Objects.requireNonNull(driver.getPageSource()).contains("Paul"));
    }

    @Test(dependsOnMethods = "testEditPerson")
    @Description("Suppression d'une personne")
    @Story("Suppression d'une personne")
    @Order(3)
    public void testDeletePerson() {

        driver.get("http://localhost:9001/");  // Ajout : Charger la page d'accueil
        personPage = new PersonPage(driver);

        int initialCount = driver.findElements(By.cssSelector("table tbody tr")).size();
        personPage.clickDeleteFirstPerson();
        int newCount = driver.findElements(By.cssSelector("table tbody tr")).size();
        Assertions.assertEquals(newCount, initialCount - 1);
        Assertions.assertFalse(Objects.requireNonNull(driver.getPageSource()).contains("Paul"));
    }
}
