package steps;

import io.cucumber.java.en.*;
import pages.LoginPage;
import utils.UserApi;

public class LoginTest {

    private final LoginPage loginPage;
    private String email;
    private String password;

    public LoginTest(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    @Given("существует пользователь с email и паролем")
    public void createUserViaApi() {
        var user = UserApi.createRandomUser();
        email = user.getEmail();
        password = user.getPassword();
    }

    @Given("открыта страница авторизации")
    public void openLoginPage() {
        loginPage.openPage();
    }

    @When("пользователь вводит email и пароль")
    public void enterCredentials() {
        loginPage.login(email, password);
        loginPage.openLogoButton();
    }

    @Then("он видит главную страницу и поиск")
    public String verifySearchMessage() {
        return loginPage.getSearchInputText();
    }
}

