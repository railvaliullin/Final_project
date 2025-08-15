package config;

public class ApiConfig {
    public static final String BASE_URL = "https://qa-desk.stand.praktikum-services.ru";
    public static final String SIGNUP_ENDPOINT = "/api/signup";
    public static final String SIGNIN_ENDPOINT = "/api/signin";
    public static final String PROFILE_ENDPOINT = "/api/profile";

    public static final String DEFAULT_PASSWORD = "password123";

    public static final String ERROR_NO_USER_ID = "Не удалось получить userId из ответа при создании пользователя";
    public static final String ERROR_NO_ACCESS_TOKEN = "Не удалось получить accessToken из ответа";
}

