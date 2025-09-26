package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiAssertions {

    // Example: POST request assertion
    public static void assertPostUser(String baseUrl, String token, String email, String name) {
        RestAssured.baseURI = baseUrl;

        String requestBody = "{\n" +
                "  \"email\": \"" + email + "\",\n" +
                "  \"name\": \"" + name + "\"\n" +
                "}";

        Response response = given()
                .header("Authorization", "Bearer " + token)   // if your API uses JWT / OAuth
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/users")   // <-- endpoint
                .then()
                .statusCode(201)  // assert response code
                .body("email", equalTo(email))  // assert JSON field
                .extract().response();

        System.out.println("POST Response: " + response.asPrettyString());
    }

    // Example: PUT request assertion
    public static void assertPutUser(String baseUrl, String token, String userId, String newName) {
        RestAssured.baseURI = baseUrl;

        String requestBody = "{\n" +
                "  \"name\": \"" + newName + "\"\n" +
                "}";

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .put("/users/" + userId)   // <-- endpoint with ID
                .then()
                .statusCode(200)
                .body("name", equalTo(newName)) // check updated field
                .extract().response();

        System.out.println("PUT Response: " + response.asPrettyString());
    }
}
