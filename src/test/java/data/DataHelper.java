package data;

import com.google.gson.Gson;
import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }

    private static class CardNumber {
        static final String cardOne = "5559 0000 0000 0001";
        static final String cardTwo = "5559 0000 0000 0002";
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;

        public static AuthInfo getAuthInfo() {
            return new AuthInfo("vasya", "qwerty123");
        }

        public static AuthInfo getAnotherAuthInfo() {
            return new AuthInfo("petya", "123qwerty");
        }
    }

    @Value
    public static class Verification {
        String login;
        String code;
    }

    @Value
    public static class Transaction {
        String from;
        String to;
        String amount;

        public static String makeTransaction(TransactionProperties props) {
            return (new Gson().toJson(new Transaction(props.sourceCard, props.targetCard, props.amount)));
        }
    }

    @Value
    public static class TransactionProperties {
        String sourceCard;
        String targetCard;
        String amount;

        public static TransactionProperties fromFirstToSecond(int amount) {
            return new TransactionProperties(CardNumber.cardOne, CardNumber.cardTwo, Integer.toString(amount));
        }

        public static TransactionProperties fromSecondToFirst(int amount) {
            return new TransactionProperties(CardNumber.cardTwo, CardNumber.cardOne, Integer.toString(amount));
        }

        public static TransactionProperties custom(String sourceCard, String targetCard, String amount) {
            return new TransactionProperties(sourceCard, targetCard, amount);
        }
    }
}