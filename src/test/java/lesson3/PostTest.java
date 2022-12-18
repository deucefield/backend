package lesson3;

import io.restassured.RestAssured;
import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.lessThan;

public class PostTest extends AbstractTest {

    @BeforeAll
    static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void checkStatusCode() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "Very Fckn Spiced Chicken")
                .queryParam("ingredientList", "A Lot Of Fck Spices")
                .when()
                .post(getBaseUrl()+"recipes/cuisine")
                .then()
                .statusCode(200);
    }

    @Test
    void checkStatusCodeName() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "Very Fckn Spiced Chicken")
                .queryParam("ingredientList", "A Lot Of Fck Spices")
                .when()
                .post(getBaseUrl()+"recipes/cuisine")
                .then()
                .statusLine(containsString("OK"));
    }

    @Test
    void checkResponseTimeLess1000ms() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "Very Fckn Spiced Chicken")
                .queryParam("ingredientList", "A Lot Of Fck Spices")
                .when()
                .post(getBaseUrl()+"recipes/cuisine")
                .then()
                .time(lessThan(1000L));
    }

    @Test
    void checkCuisine() {
        ResponseBody response =  given()
                .contentType("application/x-www-form-urlencoded")
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "Very Fckn Spiced Chicken")
                .queryParam("ingredientList", "A Lot Of Fck Spices")
                .when()
                .post(getBaseUrl()+"recipes/cuisine")
                .body();
        assertThat(response.asString(), containsString("Mediterranean"));
    }

    @Test
    void statusCode500WhenNotEnOrDeLanguage() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "Very Fckn Spiced Chicken")
                .queryParam("ingredientList", "A Lot Of Fck Spices")
                .queryParam("language", "ru")
                .when()
                .post(getBaseUrl()+"recipes/cuisine")
                .prettyPeek()
                .then()
                .statusCode(500);
    }

}
