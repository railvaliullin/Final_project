package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class RegistrationPage {
    private SelenideElement emailField = $("[name='email']");
    private SelenideElement passwordField = $("[name='password']");
    private SelenideElement passwordRepeate = $("[name='submitPassword']");
    private SelenideElement submitButton = $("button[type='submit']").shouldHave(text("Создать аккаунт"));

    public void register(String email, String password) {
        emailField.setValue(email);
        passwordField.setValue(password);
        passwordRepeate.setValue(password);
        submitButton.click();
    }
}

