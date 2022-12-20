package lesson3And4;

import io.restassured.RestAssured;
import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetTest extends AbstractTest {

    @BeforeAll
    static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void checkStatusCode() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("addRecipeNutrition", "true")
                .when()
                .get(getBaseUrl()+"recipes/complexSearch")
                .then()
                .statusCode(200);
    }

    @Test
    void checkStatusCodeName() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("addRecipeNutrition", "true")
                .when()
                .get(getBaseUrl()+"recipes/complexSearch")
                .then()
                .statusLine(containsString("OK"));
    }

    @Test
    void checkResponseTimeLess1000ms() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("addRecipeNutrition", "true")
                .when()
                .get(getBaseUrl()+"recipes/complexSearch")
                .then()
                .time(lessThan(1000L));
    }

    @Test
    void receivingCalories() {
        ResponseBody response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("addRecipeNutrition", "true")
                .when()
                .get(getBaseUrl()+"recipes/complexSearch")
                .body();
        assertThat(response.asString(), containsString("Calories"));

    }

    @Test
    void checkStatusCodeWhenUnauthorized() {
        given()
                .queryParam("apiKey", "invalid")
                .queryParam("addRecipeNutrition", "true")
                .when()
                .get(getBaseUrl()+"recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(401)
                .statusLine(containsStringIgnoringCase("UnAuthorIzed"));;
    }
}
