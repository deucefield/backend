package lesson3;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SecondTask extends AbstractTest {

    static String id = "";

    @BeforeAll
    static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void addToShoppingList() {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("hash", "540b7ebc82de5db6e801469338d6081c5b0cb0a5")
                .pathParam("username", "deucefieldo")
                .body("{\n"
                        + "\"item\": 1 package fucking powder,\n"
                        + "\"aisle\": fucking,\n"
                        + "\"parse\": true,\n"
                +"\n}")
                .when()
                .post(getBaseUrl()+"mealplanner/{username}/shopping-list/items")
                .jsonPath();
        assertThat(response.get("name"), equalTo("fucking powder"));
        assertThat(response.get("aisle"), equalTo("fucking"));

        id = response.get("id").toString();
    }

    @AfterAll
    static void tearDown() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("hash", "540b7ebc82de5db6e801469338d6081c5b0cb0a5")
                .pathParam("username", "deucefieldo")
                .pathParam("id", id)
                .when()
                .delete(getBaseUrl()+"mealplanner/{username}/shopping-list/items/{id}")
                .prettyPeek();
    }

}
