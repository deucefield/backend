package lesson3And4;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Mealplanner extends AbstractTest {

    @BeforeAll
    static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void addToShoppingList() {
        Response response = given()
                .body("{\n"
                        + "\"item\": 1 package fucking powder,\n"
                        + "\"aisle\": fucking,\n"
                        + "\"parse\": true,\n"
                +"\n}")
                .when()
                .post(getBaseUrl()+"mealplanner/{username}/shopping-list/items")
                .then()
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.get("name"), equalTo("fucking powder"));
        assertThat(jsonPath.get("aisle"), equalTo("fucking"));

        setId(jsonPath.get("id"));
        System.out.println("ID добавленного предмета: " + getId() + "\n");
    }

    @AfterEach
    void deleteFromShoppingList() {
        given()
                .pathParam("id", getId())
                .when()
                .delete(getBaseUrl()+"mealplanner/{username}/shopping-list/items/{id}");
    }

    @AfterAll
    static void getAllFromShopingList() {
        given()
                .get(getBaseUrl()+"mealplanner/{username}/shopping-list")
                .prettyPeek();
    }

}
