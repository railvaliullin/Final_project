package steps;

import io.cucumber.java.en.*;
import pages.AdsPage;


public class EditTest {
    private final AdsPage adsPage;
    private String adTitle;

    public EditTest(AdsPage adsPage) {
        this.adsPage = adsPage;
    }

    @Given("у пользователя есть созданное объявление с заголовком {string}")
    public void givenAdExists(String existingTitle) {
        this.adTitle = existingTitle;
        adsPage.waitForAdToAppear(adTitle);
        adsPage.clickSvgSmallIcon();
    }

    @When("он открывает редактирование этого объявления")
    public void openEditAdPage() {
        adsPage.goToProfilePage();
        adsPage.openEditAdPage(adTitle);
    }

    @And("он изменяет заголовок и описание")
    public void editAdTitle() {
        adTitle = "Обновлённое_" + System.currentTimeMillis();
        adsPage.fillAdForm(adTitle, "Обновлённое описание");
    }

    @Then("он сохраняет изменения объявления")
    public void saveAdChanges() {
        adsPage.saveAd();
    }
}

