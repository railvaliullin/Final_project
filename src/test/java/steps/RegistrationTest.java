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
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationTest {

    private String generatedEmail;
    private String userId;
    private String accessToken;

    @Given("тестовый пользователь создан через API")
    public void createTestUserViaApi() {
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

    @When("пользователь авторизуется в системе через API")
    public void loginUserViaApi() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", generatedEmail);
        requestBody.put("password", ApiConfig.DEFAULT_PASSWORD);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(ApiConfig.SIGNIN_ENDPOINT)
                .then()
                .statusCode(201)
                .extract()
                .response();

        accessToken = response.jsonPath().getString("token.access_token");

        if (accessToken == null || accessToken.isEmpty()) {
            throw new IllegalStateException(ApiConfig.ERROR_NO_ACCESS_TOKEN);
        }
    }

    @Then("пользователь существует в системе")
    public void checkUserExists() {
        if (accessToken == null || accessToken.isEmpty()) {
            throw new IllegalStateException(ApiConfig.ERROR_NO_ACCESS_TOKEN);
        }

        Response response = given()
                .baseUri(ApiConfig.BASE_URL)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get(ApiConfig.PROFILE_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .response();

        String returnedUserId = response.jsonPath().getString("id");
        assertEquals(userId, returnedUserId);
    }
}
