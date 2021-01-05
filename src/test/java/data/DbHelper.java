package data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class DbHelper {
    private final static Connection conn = establishConnection();
    private final static QueryRunner runn = new QueryRunner();

    private DbHelper() {
    }

    @SneakyThrows
    private static Connection establishConnection() {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/deadline", "mrtotalsecurity", "CzmGtmRjc3cLGV7KXza294520qCMYXuF");
    }

    @SneakyThrows
    public static String getCodeAsIs() {
        List<Codes> codesList = runn.query(conn, "SELECT code FROM auth_codes ORDER BY created;", new BeanListHandler<>(Codes.class));
        return codesList.get(codesList.size() - 1).toString();
    }

    @SneakyThrows
    public static int cardOneBalanceDb() {
        return runn.query(conn, "SELECT balance_in_kopecks FROM cards WHERE number LIKE '%01';", new ScalarHandler<>());
    }

    @SneakyThrows
    public static int cardTwoBalanceDb() {
        return runn.query(conn, "SELECT balance_in_kopecks FROM cards WHERE number LIKE '%02';", new ScalarHandler<>());
    }

    @SneakyThrows
    public static String getCode() {
        return runn.query(conn, "SELECT code FROM auth_codes", new ScalarHandler<>());
    }

    @SneakyThrows
    public static void wipeCodes() {
        runn.execute(conn, "TRUNCATE auth_codes");
    }

    @SneakyThrows
    public static void wipeEverything() {
        runn.execute(conn, "TRUNCATE auth_codes");
        runn.execute(conn, "TRUNCATE cards;");
        runn.execute(conn, "TRUNCATE card_transactions;");
        runn.execute(conn, "DELETE FROM users WHERE status LIKE '%ive';");
    }
}