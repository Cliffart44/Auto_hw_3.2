package test;

import data.Codes;
import io.restassured.http.ContentType;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.jupiter.api.*;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.*;
import static data.DataHelper.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PageUiTest {
    static QueryRunner runner = new QueryRunner();
    static int transactionAmount = 5000;
    static int starterBalanceCardOne;
    static int starterBalanceCardTwo;
    static Connection conn;
    static String token;
    static String code;

    @BeforeAll
    static void createConnection() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/deadline", "mrtotalsecurity", "CzmGtmRjc3cLGV7KXza294520qCMYXuF");
        starterBalanceCardOne = runner.query(conn, "SELECT balance_in_kopecks FROM cards WHERE number LIKE '%01';", new ScalarHandler<>());
        starterBalanceCardTwo = runner.query(conn, "SELECT balance_in_kopecks FROM cards WHERE number LIKE '%02';", new ScalarHandler<>());
    }

    @Test
    @Order(1)
    void shouldLogIn() throws SQLException {
        User.userRegistration();
        List<Codes> codesList = runner.query(conn, "SELECT code FROM auth_codes ORDER BY created;", new BeanListHandler<>(Codes.class));
        code = codesList.get(codesList.size() - 1).toString();
        token = Verification.userOne(code);
    }

    @Test
    @Order(2)
    void shouldGetCards() {
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

    @Test
    @Order(3)
    void shouldMakeTransaction() throws SQLException {
        given()
                .baseUri("http://localhost:9999/api")
                .headers(
                        "Authorization", "Bearer " + token,
                        "Content-Type", ContentType.JSON)
                .body(Transaction.act(Integer.toString(transactionAmount)))
                .when()
                .post("/transfer")
                .then()
                .statusCode(200);
        int finitesimalBalanceCardOne = runner.query(conn, "SELECT balance_in_kopecks FROM cards WHERE number LIKE '%01';", new ScalarHandler<>());
        int finitesimalBalanceCardTwo = runner.query(conn, "SELECT balance_in_kopecks FROM cards WHERE number LIKE '%02';", new ScalarHandler<>());
        assertEquals(finitesimalBalanceCardOne, starterBalanceCardOne + (transactionAmount * 100));
        assertEquals(finitesimalBalanceCardTwo, starterBalanceCardTwo - (transactionAmount * 100));
    }

    @Test
    @Order(4)
    void shouldLogInAnotherUser() throws SQLException {
        User.anotherUserRegistration();
        List<Codes> codesList = runner.query(conn, "SELECT code FROM auth_codes ORDER BY created;", new BeanListHandler<>(Codes.class));
        code = codesList.get(codesList.size() - 1).toString();
        token = Verification.userTwo(code);
    }

    @Test
    @Order(5)
    void shouldMakeTransactionAnotherUser() throws SQLException {
        given()
                .baseUri("http://localhost:9999/api")
                .headers(
                        "Authorization", "Bearer " + token,
                        "Content-Type", ContentType.JSON)
                .body(Transaction.actAnotherUser(Integer.toString(transactionAmount)))
                .when()
                .post("/transfer")
                .then()
                .statusCode(200);
        int finitesimalBalanceCardOne = runner.query(conn, "SELECT balance_in_kopecks FROM cards WHERE number LIKE '%01';", new ScalarHandler<>());
        int finitesimalBalanceCardTwo = runner.query(conn, "SELECT balance_in_kopecks FROM cards WHERE number LIKE '%02';", new ScalarHandler<>());
        assertEquals(finitesimalBalanceCardOne, finitesimalBalanceCardTwo);
    }

    @Test
    @Order(6)
    @Disabled
    void shouldGetCardsAnotherUser() {
        int cardOne = starterBalanceCardOne / 100;
        int cardTwo = starterBalanceCardTwo / 100;
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
                        "    \"balance\": " + cardTwo + "\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"id\": \"92df3f1c-a033-48e6-8390-206f6b1f56c0\",\n" +
                        "    \"number\": \"**** **** **** 0001\",\n" +
                        "    \"balance\": " + cardOne + "\n" +
                        "  }\n" +
                        "]"));
    }

    @Test
    @Order(7)
    @Disabled
    void shouldNotMakeTransactionAnotherUser() {
        given()
                .baseUri("http://localhost:9999/api")
                .headers(
                        "Authorization", "Bearer " + token,
                        "Content-Type", ContentType.JSON)
                .body(Transaction.actAnotherUser(Integer.toString(++starterBalanceCardOne)))
                .when()
                .post("/transfer")
                .then()
                .statusCode(400);
    }
}
