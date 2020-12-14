package test;

import lombok.val;
import org.junit.jupiter.api.*;
import page.LoginPage;

import java.sql.Connection;

import static com.codeborne.selenide.Selenide.open;
import static data.DataHelper.*;
import static data.DbHelper.*;


public class PageUiTest {
    final Connection conn = establishConnection();

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @AfterEach
    void cleanUp() {
        wipeCodes(conn);
    }

    @Test
    void shouldLogIn() {
        val verificationPage = new LoginPage().validLogin(getAuthInfo());
        verificationPage.validVerify(getCode(conn));
    }

    @Test
//    @Disabled
    void shouldBeBlocked() {
        new LoginPage().tripleInvalidLogin(getAnotherAuthInfo());
    }
}