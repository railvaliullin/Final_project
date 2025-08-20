package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {
    private final SelenideElement emailField = $("[name='email']");
    private final SelenideElement passwordField = $("[name='password']");
    private final SelenideElement loginButton = $x("//button[normalize-space()='Войти']");
    private final String searchInput = "//input[@placeholder='Я хочу купить...']";
    private final SelenideElement createAdButton = $x("//button[normalize-space()='Разместить объявление']");
    private final SelenideElement logoButton = $("svg.header_logo__yAp5Y");


    public void openPage() {
        open("https://qa-desk.stand.praktikum-services.ru/login");
    }

    public void login(String email, String password) {
        emailField.setValue(email);
        passwordField.setValue(password);
        loginButton.click();
    }

    public String getSearchInputText() {
        return $x(searchInput).should(appear).getText();
    }

    public void openLogoButton() {
        logoButton.click();
    }
}

