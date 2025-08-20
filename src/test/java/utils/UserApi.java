package utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserApi {

    static {
        RestAssured.baseURI = "https://qa-desk.stand.praktikum-services.ru";
    }

    public static TestUser createRandomUser() {
        String email = "test" + System.currentTimeMillis() + "@mail.com";
        String password = "password123";

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("password", password);

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/signup")
                .then()
                .statusCode(201)
                .body("user.email", equalTo(email));

        return new TestUser(email, password);
    }
}

