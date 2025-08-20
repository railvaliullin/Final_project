package steps;

import io.cucumber.java.en.*;
import pages.AdsPage;
import pages.LoginPage;
import utils.UserApi;

public class CreateTest {
    private final AdsPage adsPage;
    private final LoginPage loginPage;

    private String email;
    private String password;
    private String adTitle;

    public CreateTest(AdsPage adsPage, LoginPage loginPage) {
        this.adsPage = adsPage;
        this.loginPage = loginPage;
    }

    @Given("пользователь авторизован для создания объявления")
    public void userIsLoggedInForCreation() {
        var user = UserApi.createRandomUser();
        email = user.getEmail();
        password = user.getPassword();

        loginPage.openPage();
        loginPage.login(email, password);
        loginPage.openLogoButton();
    }

    @When("он открывает страницу создания объявления")
    public void openCreateAdPage() {
        adsPage.openCreateAdPage();
    }

    @And("он заполняет форму нового объявления")
    public void fillAdForm() {
        adTitle = "Объявление_" + System.currentTimeMillis();
        adsPage.fillAdForm(adTitle, "Описание тестового объявления");
    }

    @Then("он сохраняет новое объявление")
    public void saveAd() {
        adsPage.saveAd();
    }
}
