package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class AdsPage {

    private final SelenideElement createAdButton = $x(".//button[normalize-space()='Разместить объявление']");
    private final SelenideElement titleField = $x("//input[@placeholder='Название' and @name='name']");
    private final SelenideElement descriptionField = $("[placeholder='Описание товара']");
    private final SelenideElement saveButton = $x("//button[normalize-space()='Опубликовать']");
    private final SelenideElement svgSmallIcon = $("button.circleSmall");

    public void openCreateAdPage() {
        createAdButton.scrollIntoView(true).shouldBe(visible).click();

        titleField.should(appear, Duration.ofSeconds(5));
        descriptionField.should(appear, Duration.ofSeconds(5));
    }

    public void fillAdForm(String title, String description) {
        titleField.shouldBe(visible).setValue(title);
        descriptionField.shouldBe(visible).setValue(description);
    }

    public void saveAd() {
        saveButton.shouldBe(visible).click();
    }


    public void openEditAdPage(String adTitle) {
        String editButtonXpath = String.format(
                "button svg.editIcon",
                adTitle
        );
        $(editButtonXpath)
                .should(appear, Duration.ofSeconds(5))
                .scrollIntoView(true)
                .click();
    }

    public void waitForAdToAppear(String adTitle) {
        $x("//*[@data-testid='ad-title' and text()='" + adTitle + "']")
                .should(appear, Duration.ofSeconds(5));
    }

    public boolean isAdPresent(String adTitle) {
        return $$("[data-testid='ad-title']")
                .findBy(text(adTitle))
                .exists();
    }

    public void clickSvgSmallIcon() {
        svgSmallIcon.should(appear)
                .shouldBe(enabled)
                .scrollIntoView(true);
        executeJavaScript("arguments[0].click();", svgSmallIcon);
    }


    public void goToProfilePage() {
        open("https://qa-desk.stand.praktikum-services.ru/profile");
        Selenide.sleep(1000);
        executeJavaScript("window.scrollTo(0, document.body.scrollHeight)");
        Selenide.sleep(500);
    }
}
