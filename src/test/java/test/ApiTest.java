package test;

import org.junit.jupiter.api.*;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static data.DataHelper.AuthInfo.*;
import static data.DataHelper.TransactionProperties.*;
import static data.DbHelper.*;
import static data.ApiHelper.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiTest {
    static final Connection conn = establishConnection();
    static final int starterCardOneBalance = cardOneBalance(conn);
    static final int starterCardTwoBalance = cardTwoBalance(conn);
    static final int transactionAmount = 5000;
    static String token;

    @Test
    @Order(1)
    void shouldLogIn() {
        logIn(getAuthInfo());
        token = verification(getAuthInfo().getLogin(), getCodeAsIs(conn));
    }

    @Test
    @Order(2)
    void shouldGetCards() {
        getCards(token);
    }

    @Test
    @Order(3)
    void shouldMakeTransaction() {
        makeTransaction(token, fromFirstToSecond(transactionAmount), 200);
        assertEquals(starterCardOneBalance - (transactionAmount * 100), cardOneBalance(conn));
        assertEquals(starterCardTwoBalance + (transactionAmount * 100), cardTwoBalance(conn));
    }

    @Test
    @Order(4)
    void shouldLogInAnotherUser() {
        logIn(getAnotherAuthInfo());
        token = verification(getAnotherAuthInfo().getLogin(), getCodeAsIs(conn));
    }

    @Test
    @Order(5)
    void shouldMakeTransactionAnotherUser() {
        makeTransaction(token, fromSecondToFirst(transactionAmount), 200);
        assertEquals(cardOneBalance(conn), cardTwoBalance(conn));
    }

    @Test
    @Order(6)
    @Disabled
    void shouldGetCardsAnotherUser() {
        getCardsWithCustomBody(cardOneBalance(conn), cardTwoBalance(conn), token);
    }

    @Test
    @Order(7)
    @Disabled
    void shouldNotMakeTransactionAnotherUser() {
        makeTransaction(token, fromFirstToSecond(starterCardOneBalance + 1), 400);
    }
}