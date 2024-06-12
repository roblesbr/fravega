package apis;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static utils.settings.Settings.*;

public class BaseApiTest {

    private static Response response;
    private static RequestSpecification httpRequest;
    private static String strResponse;
    private static String firstUserId;

    public static void setBaseUri() {
        // Set the base URI for the REST Assured requests
        RestAssured.baseURI = baseUrl;
    }

    public static RequestSpecification authTwitter() {
        // Authenticate to Twitter API using OAuth credentials
        httpRequest = given().auth().oauth(consumerKey, consumerSecret, accessToken, accessTokenSecret);
        return httpRequest;
    }

    public static Response sendGetRequest(String url) {
        // Send a GET request and verify response status and content type
        return given().get(url).then().assertThat().statusCode(200)
                .and().contentType(ContentType.JSON).extract().response();
    }

    public static Response updateUser(Map<String, Object> user) {
        // Send a GET request to obtain the ID of the first user
        Response getResponse = given()
                .header("Authorization", bearerToken) // Bearer Token
                .get("https://gorest.co.in/public/v1/users")
                .then().extract().response();

        // Verify the status code of the GET response
        int statusCode = getResponse.getStatusCode();
        if (statusCode != 200) {
            throw new RuntimeException("Error while fetching the list of users. Status code: " + statusCode);
        }

        // Get the ID of the first user
        firstUserId = getResponse.jsonPath().getString("data[0].id");

        // Print the ID for verification
        System.out.println("The ID of the first user is: " + firstUserId);

        // Send a PATCH request using the ID of the first user
        Response patchResponse = given()
                .header("Authorization", bearerToken)
                .contentType(ContentType.JSON)
                .body(user)
                .patch("https://gorest.co.in/public/v1/users/" + firstUserId)
                .then().assertThat().statusCode(200).extract().response();

        // Print the server response body
        System.out.println("Server Response:");
        System.out.println(patchResponse.getBody().asString());

        // Return the response of the PATCH request
        return patchResponse;
    }
}
