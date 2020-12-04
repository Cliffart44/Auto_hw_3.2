package test;

import lombok.val;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.apache.commons.dbutils.QueryRunner;
import page.LoginPage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static data.DataHelper.*;

public class PageUiTest {
    static QueryRunner runner = new QueryRunner();
    static Connection conn;

    @BeforeAll
    static void createConnection() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/deadline", "mrtotalsecurity", "CzmGtmRjc3cLGV7KXza294520qCMYXuF");
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLogIn() throws SQLException {
        val verificationPage = new LoginPage().validLogin(getAuthInfo());
        verificationPage.validVerify(runner.query(conn, "SELECT code FROM auth_codes", new ScalarHandler<>()));
        runner.execute(conn, "TRUNCATE auth_codes");
    }
}
