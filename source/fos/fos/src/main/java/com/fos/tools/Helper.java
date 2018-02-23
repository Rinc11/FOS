package com.fos.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Function;
import java.util.function.Supplier;

public class Helper {
    private Helper() {
    }

    public static <T> T nullValue(Supplier<T> normalCall, Supplier<T> nullValueCall) {
        T result = normalCall.get();
        if (result == null) {
            result = nullValueCall.get();
        }
        return result;
    }

    public static <T, R> R nullCheck(T object, Function<T, R> function) {
        if (object == null) {
            return null;
        }
        return function.apply(object);
    }

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres&ssl=false";
        try {
            Class.forName("org.postgresql.Driver");

            Connection conn = DriverManager.getConnection(url);
            conn.setSchema("fos");
            return conn;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
