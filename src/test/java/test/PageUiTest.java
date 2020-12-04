package test;

import data.Codes;
import data.DataHelper;
import lombok.val;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.apache.commons.dbutils.QueryRunner;
import page.LoginPage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static com.codeborne.selenide.Selenide.open;

public class PageUiTest {
    String code;
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
    void shouldLogInAndKeepCodes() throws SQLException {
        val verificationPage = new LoginPage().validLogin(DataHelper.getAuthInfo());
        List<Codes> codesList = new QueryRunner().query(conn, "SELECT code FROM auth_codes ORDER BY created;", new BeanListHandler<>(Codes.class));
        code = codesList.get(codesList.size() - 1).toString();
        verificationPage.validVerify(code);
    }

    @Disabled
    @Test
    void shouldLogInAndWipeCode() throws SQLException {
        val verificationPage = new LoginPage().validLogin(DataHelper.getAuthInfo());
        code = new QueryRunner().query(conn, "SELECT code FROM auth_codes", new ScalarHandler<>());
        verificationPage.validVerify(code);
        new QueryRunner().execute(conn, "TRUNCATE auth_codes");
    }
}
