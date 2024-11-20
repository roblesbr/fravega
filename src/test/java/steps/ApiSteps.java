package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static utils.settings.Settings.apiUrl;

public class ApiSteps {

    private Response response;
    private static int userId;
    private static String authToken = "fa637faf8f51ad59aceb0ab41d00d298327fe67973974631569aa0e90d0234c9";  // Token de autenticación

    @Given("I have access to the GoRest API")
    public void givenAccessToGoRestAPI() {
        RestAssured.baseURI = apiUrl;
    }

    @When("I send a POST request to create a new user with valid data")
    public void createNewUser() {
        String requestBody = "{\n" +
                "    \"name\": \"John Doe\",\n" +
                "    \"email\": \"johndoe" + System.currentTimeMillis() + "@example.com\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"status\": \"active\"\n" +
                "}";

        // Hacer el POST con los encabezados adecuados
        response = given()
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + authToken) // Autenticación con token
                .body(requestBody)
                .when()
                .post()
                .then()
                .statusCode(201)  // Comprobamos que la respuesta sea 201
                .extract().response();

        // Imprimir la respuesta para verificar la estructura
        String responseBody = response.getBody().asString();
        System.out.println("Response Body: " + responseBody);

        // Acceder correctamente al "id" dentro del objeto "data"
        try {
            userId = response.jsonPath().getInt("data.id");
            System.out.println("Created user ID: " + userId);
        } catch (Exception e) {
            System.out.println("Error extracting user ID: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Then("I should receive a 201 response and a user ID")
    public void verifyUserCreated() {
        Assert.assertTrue(userId > 0, "User ID should be greater than 0");
    }

    @When("I send a GET request to fetch the list of users")
    public void fetchUsersList() {
        // Enviamos una solicitud GET para recuperar la lista de usuarios
        response = given()
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + authToken)  // Agregamos el token de autenticación
                .when()
                .get(apiUrl)  // Realizamos el GET a la URL del API (obtenemos la lista de usuarios)
                .then()
                .statusCode(200)  // Esperamos recibir un código 200, lo que indica éxito
                .extract().response();  // Extraemos la respuesta para usarla en las siguientes verificaciones
    }

    @Then("I should receive a 200 response and a list containing the created user")
    public void verifyUserInList() {
        Response response = given()
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .when()
                .get(apiUrl) // Asegúrate de que esta URL sea la correcta
                .then()
                .statusCode(200) // Esperamos un código 200
                .extract().response();

        // Imprimir el cuerpo de la respuesta para depuración
        String responseBody = response.getBody().asString();
        System.out.println("Response Body: " + responseBody);

        // Verifica que la respuesta contenga el ID del usuario creado
        given()
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .when()
                .get(apiUrl) // Utiliza la URL base para obtener los usuarios
                .then()
                .statusCode(200)
                .body("data.id", hasItem(userId)); // Verifica que el id del usuario creado esté en la lista
    }

    @When("I send a GET request to fetch the details of the created user by ID")
    public void fetchUserDetails() {
        response = given()
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .when()
                .get(apiUrl + "/" + userId) // Obtener los detalles del usuario creado
                .then()
                .statusCode(200)  // Esperamos un código 200
                .extract().response();
    }

    @Then("I should receive a 200 response with the correct user details")
    public void verifyUserDetails() {
        response = given()
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .when()
                .get(apiUrl + "/" + userId)
                .then()
                .statusCode(200)  // Verificamos que la respuesta sea 200
                .body("data.id", is(userId))  // Verifica que el ID en "data" coincida con el userId
                .body("data.name", not(""))  // Verifica que el nombre no esté vacío
                .body("data.email", not(""))  // Verifica que el correo no esté vacío
                .body("data.name", is(not(nullValue())))  // Verifica que el nombre no sea null
                .body("data.email", is(not(nullValue())))  // Verifica que el correo no sea null
                .extract().response();  // Extrae la respuesta para depurar

        // Imprimir el cuerpo de la respuesta para ver qué datos se están recibiendo
        System.out.println("Response Body: " + response.getBody().asString());
    }

}
