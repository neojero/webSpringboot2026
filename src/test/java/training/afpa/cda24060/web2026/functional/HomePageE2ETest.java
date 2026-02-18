package training.afpa.cda24060.web2026.functional;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;


@Feature("Tests d'interface utilisateur")
public class HomePageE2ETest extends BaseE2ETest {

    @Test
    @Description("Vérifie que le titre de la page d'accueil est correct")
    @Story("Affichage de la page d'accueil")
    public void testHomePageTitle() {
        driver.get("http://localhost:9001"); // Remplace par l'URL de ton application
        String expectedTitle = "Person Web Application";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle, "Le titre de la page ne correspond pas");
    }

    @Test
    @Description("Vérifie la présence d'un élément spécifique sur la page")
    @Story("Contenu de la page d'accueil")
    public void testHomePageElement() {
        driver.get("http://localhost:9001");
        boolean isElementPresent = !driver.findElements(By.className("h2")).isEmpty();
        Assert.assertTrue(isElementPresent, "L'élément attendu n'est pas présent sur la page");
    }

}
