package sample;

import java.sql.*;

public class MySQLConnUtils {
    public static Connection getJDBCConnection() {
        final String url;
        url = "jdbc:mysql://localhost:3306/dictionary";
        final String user = "root";
        final String password = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("thanh cong");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.out.println("loi");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("loi");
            e.printStackTrace();
        }
        return null;
    }

    /*public static void main(String[] args) {
            getJDBCConnection();
    }*/
}
