package data;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import lombok.Value;

import static io.restassured.RestAssured.given;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    @Value
    public static class User {
        private static final String username = "vasya";
        private static final String password = "qwerty123";
        private static final String anotherUsername = "petya";
        private static final String anotherPassword = "123qwerty";

        public static void userRegistration() {
            given()
                    .baseUri("http://localhost:9999")
                    .contentType(ContentType.JSON)
                    .body(new Gson().toJson(new AuthInfo(username, password)))
                    .when()
                    .post("/api/auth")
                    .then()
                    .statusCode(200);
        }

        public static void anotherUserRegistration() {
            given()
                    .baseUri("http://localhost:9999")
                    .contentType(ContentType.JSON)
                    .body(new Gson().toJson(new AuthInfo(anotherUsername, anotherPassword)))
                    .when()
                    .post("/api/auth")
                    .then()
                    .statusCode(200);
        }
    }

    @Value
    public static class Verification {
        String login;
        String code;

        public static String userOne(String code) {
            return given()
                    .baseUri("http://localhost:9999/api")
                    .contentType(ContentType.JSON)
                    .body(new Gson().toJson(new Verification(User.username, code)))
                    .when()
                    .post("/auth/verification")
                    .then()
                    .statusCode(200)
                    .extract()
                    .path("token")
                    .toString();
        }

        public static String userTwo(String code) {
            return given()
                    .baseUri("http://localhost:9999/api")
                    .contentType(ContentType.JSON)
                    .body(new Gson().toJson(new Verification(User.anotherUsername, code)))
                    .when()
                    .post("/auth/verification")
                    .then()
                    .statusCode(200)
                    .extract()
                    .path("token")
                    .toString();
        }
    }

    @Value
    public static class Transaction {
        String from;
        String to;
        String amount;

        public static String act(String amount) {
            return (new Gson().toJson(new Transaction("5559 0000 0000 0002", "5559 0000 0000 0001", amount)));
        }

        public static String actAnotherUser(String amount) {
            return (new Gson().toJson(new Transaction("5559 0000 0000 0001", "5559 0000 0000 0002", amount)));
        }
    }
}
