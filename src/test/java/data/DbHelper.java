package data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class DbHelper {
    private static final QueryRunner runner = new QueryRunner();

    private DbHelper() {
    }

    @SneakyThrows
    public static Connection establishConnection() {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/deadline", "mrtotalsecurity", "CzmGtmRjc3cLGV7KXza294520qCMYXuF");
    }

    @SneakyThrows
    public static String getCode(Connection conn) {
        return runner.query(conn, "SELECT code FROM auth_codes", new ScalarHandler<>());
    }

    @SneakyThrows
    public static void wipeCodes(Connection conn) {
        runner.execute(conn, "TRUNCATE auth_codes");
    }

    @SneakyThrows
    public static String getCodeAsIs(Connection conn) {
        List<Codes> codesList = runner.query(conn, "SELECT code FROM auth_codes ORDER BY created;", new BeanListHandler<>(Codes.class));
        return codesList.get(codesList.size() - 1).toString();
    }

    @SneakyThrows
    public static int cardOneBalance(Connection conn) {
        return runner.query(conn, "SELECT balance_in_kopecks FROM cards WHERE number LIKE '%01';", new ScalarHandler<>());
    }

    @SneakyThrows
    public static int cardTwoBalance(Connection conn) {
        return runner.query(conn, "SELECT balance_in_kopecks FROM cards WHERE number LIKE '%02';", new ScalarHandler<>());
    }
}