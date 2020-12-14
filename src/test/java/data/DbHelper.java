package data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbHelper {

    private DbHelper() {
    }

    @SneakyThrows
    public static Connection establishConnection() {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/deadline", "mrtotalsecurity", "CzmGtmRjc3cLGV7KXza294520qCMYXuF");
    }

    @SneakyThrows
    public static String getCode(Connection conn) {
        return new QueryRunner().query(conn, "SELECT code FROM auth_codes", new ScalarHandler<>());
    }

    @SneakyThrows
    public static void wipeCodes(Connection conn) {
        new QueryRunner().execute(conn, "TRUNCATE auth_codes");
    }
}
