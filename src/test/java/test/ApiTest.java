package test;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static data.DataHelper.AuthInfo.*;
import static data.DataHelper.TransactionProperties.*;
import static data.DbHelper.*;
import static data.ApiHelper.*;

//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiTest {
    static final int transactionAmount = 5000;
    static String token;

    @AfterAll
    static void totalTidyUp() {
        wipeEverything();
    }

    @Test
//    @Order(1)
    void shouldMakeTransaction() {
        int starterCardOneBalance = cardOneBalance();
        int starterCardTwoBalance = cardTwoBalance();
        logIn(getAuthInfo());
        token = verification(getAuthInfo().getLogin(), getCodeAsIs());
        getCards(token);
        makeTransaction(token, fromFirstToSecond(transactionAmount), 200);
        assertEquals(starterCardOneBalance - (transactionAmount * 100), cardOneBalance());
        assertEquals(starterCardTwoBalance + (transactionAmount * 100), cardTwoBalance());
    }

    @Test
//    @Order(2)
//    @Disabled
    void shouldMakeTransactionAnotherUser() {
        int starterCardOneBalance = cardOneBalance();
        int starterCardTwoBalance = cardTwoBalance();
        logIn(getAnotherAuthInfo());
        token = verification(getAnotherAuthInfo().getLogin(), getCodeAsIs());
        getCardsWithCustomBody(cardOneBalance(), cardTwoBalance(), token);
        makeTransaction(token, fromSecondToFirst(transactionAmount), 200);
        assertEquals(starterCardOneBalance + (transactionAmount * 100), cardOneBalance());
        assertEquals(starterCardTwoBalance - (transactionAmount * 100), cardTwoBalance());
    }

    @Test
//    @Order(3)
//    @Disabled
    void shouldNotMakeTransactionAnotherUser() {
        int starterCardOneBalance = cardOneBalance();
        makeTransaction(token, fromFirstToSecond(starterCardOneBalance + 1), 400);
    }
}