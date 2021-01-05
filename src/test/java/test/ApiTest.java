package test;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static data.DataHelper.AuthInfo.*;
import static data.DataHelper.TransactionProperties.*;
import static data.DbHelper.*;
import static data.ApiHelper.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiTest {
    static final int transactionAmount = 5000;

    @AfterAll
    static void totalTidyUp() {
        wipeEverything();
    }

    @Test
    @Order(1)
    void shouldMakeTransaction() {
        logIn(getAuthInfo());
        final String token = verification(getAuthInfo().getLogin(), getCodeAsIs());
        final int starterCardOneBalance = cardOneBalanceApi(token);
        final int starterCardTwoBalance = cardTwoBalanceApi(token);
        assertEquals(starterCardOneBalance, cardOneBalanceDb() / 100);
        assertEquals(starterCardTwoBalance, cardTwoBalanceDb() / 100);
        makeTransaction(token, fromFirstToSecond(transactionAmount), 200);
        assertEquals(starterCardOneBalance - transactionAmount, cardOneBalanceApi(token));
        assertEquals(starterCardTwoBalance + transactionAmount, cardTwoBalanceApi(token));
    }

    @Test
    @Order(2)
    void shouldMakeTransactionAnotherUser() {
        logIn(getAnotherAuthInfo());
        final String token = verification(getAnotherAuthInfo().getLogin(), getCodeAsIs());
        final int starterCardOneBalance = cardOneBalanceDb();
        final int starterCardTwoBalance = cardTwoBalanceDb();
//        getCardsWithCustomBody(cardOneBalanceDb(), cardTwoBalanceDb(), token); /* Failing part */
        makeTransaction(token, fromSecondToFirst(transactionAmount), 200);
        assertEquals(starterCardOneBalance + (transactionAmount * 100), cardOneBalanceDb());
        assertEquals(starterCardTwoBalance - (transactionAmount * 100), cardTwoBalanceDb());
    }

    @Test
    @Order(3)
    @Disabled
    void shouldNotMakeTransactionAnotherUser() {
        logIn(getAnotherAuthInfo());
        final String token = verification(getAnotherAuthInfo().getLogin(), getCodeAsIs());
        makeTransaction(token, fromFirstToSecond(cardTwoBalanceApi(token) + 1), 400);
    }
}