package data;

import com.google.gson.Gson;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class ApiHelper {
    private ApiHelper() {
    }

    public static void logIn(DataHelper.AuthInfo authInfo) {
        given()
                .baseUri("http://localhost:9999/api")
                .contentType(ContentType.JSON)
                .body(new Gson().toJson(new DataHelper.AuthInfo(authInfo.getLogin(), authInfo.getPassword())))
                .when()
                .post("/auth")
                .then()
                .statusCode(200);
    }

    public static String verification(String login, String code) {
        return given()
                .baseUri("http://localhost:9999/api")
                .contentType(ContentType.JSON)
                .body(new Gson().toJson(new DataHelper.Verification(login, code)))
                .when()
                .post("/auth/verification")
                .then()
                .statusCode(200)
                .extract()
                .path("token")
                .toString();
    }

    public static void getCards(String token) {
        given()
                .baseUri("http://localhost:9999/api")
                .header(
                        "Authorization", "Bearer " + token)
                .when()
                .get("/cards")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(matchesJsonSchemaInClasspath("API_schema.json"));
    }

    public static void getCardsWithCustomBody(int cardOneBalance, int cardTwoBalance, String token) {
        given()
                .baseUri("http://localhost:9999/api")
                .header(
                        "Authorization", "Bearer " + token)
                .when()
                .get("/cards")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(equalTo("[\n" +
                        "  {\n" +
                        "    \"id\": \"0f3f5c2a-249e-4c3d-8287-09f7a039391d\",\n" +
                        "    \"number\": \"**** **** **** 0002\",\n" +
                        "    \"balance\": " + cardTwoBalance / 100 + "\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"id\": \"92df3f1c-a033-48e6-8390-206f6b1f56c0\",\n" +
                        "    \"number\": \"**** **** **** 0001\",\n" +
                        "    \"balance\": " + cardOneBalance / 100 + "\n" +
                        "  }\n" +
                        "]"));
    }

    public static void makeTransaction(String token, DataHelper.TransactionProperties props, int statusCode) {
        given()
                .baseUri("http://localhost:9999/api")
                .headers(
                        "Authorization", "Bearer " + token,
                        "Content-Type", ContentType.JSON)
                .body(DataHelper.Transaction.makeTransaction(props))
                .when()
                .post("/transfer")
                .then()
                .statusCode(statusCode);
    }
}
