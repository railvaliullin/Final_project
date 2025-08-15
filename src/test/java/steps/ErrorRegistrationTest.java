package steps;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import config.ApiConfig;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

public class ErrorRegistrationTest {

    private String generatedEmail;
    private String userId;
    private String accessToken;

    private int lastStatusCode;
    private String lastResponseBody;

    @Given("ошибочный тестовый пользователь создан через API")
    public void createErrorTestUserViaApi() {
        RestAssured.baseURI = ApiConfig.BASE_URL;

        generatedEmail = "test" + System.currentTimeMillis() + "@mail.com";

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", generatedEmail);
        requestBody.put("password", ApiConfig.DEFAULT_PASSWORD);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(ApiConfig.SIGNUP_ENDPOINT)
                .then()
                .statusCode(201)
                .body("user.email", equalTo(generatedEmail))
                .extract()
                .response();

        userId = response.jsonPath().getString("user.id");
        accessToken = response.jsonPath().getString("access_token.access_token");

        if (userId == null || userId.isEmpty()) {
            throw new IllegalStateException(ApiConfig.ERROR_NO_USER_ID);
        }
        if (accessToken == null || accessToken.isEmpty()) {
            throw new IllegalStateException(ApiConfig.ERROR_NO_ACCESS_TOKEN);
        }
    }

    @When("пользователь пытается зарегистрироваться повторно с тем же email")
    public void tryToRegisterAgainWithSameEmail() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", generatedEmail);
        requestBody.put("password", ApiConfig.DEFAULT_PASSWORD);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(ApiConfig.SIGNUP_ENDPOINT)
                .then()
                .extract()
                .response();

        lastStatusCode = response.statusCode();
        lastResponseBody = response.body().asString();
    }

    @Then("появляется ошибка о существующем пользователе")
    public void checkDuplicateUserError() {
        assertEquals(400, lastStatusCode, "Ожидался статус 400 при попытке повторной регистрации");
        assertTrue(lastResponseBody.contains("Почта уже используется") ||
                        lastResponseBody.toLowerCase().contains("уже используется"),
                "Ответ не содержит сообщение об уже существующем пользователе");
    }
}
