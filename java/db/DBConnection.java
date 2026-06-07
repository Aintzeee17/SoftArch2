package db;

import java.sql.*;

public class DBConnection {

    // URL, USER, dan PASSWORD anda sudah betul untuk Render PostgreSQL
    private static final String URL = "jdbc:postgresql://dpg-d8hhl4ddt1ts738fi29g-a.singapore-postgres.render.com:5432/softarch?ssl=true";
    private static final String USER = "softarch_user";
    private static final String PASSWORD = "GUJpOKuyzsdtwhf69G4odYmIQu2rn86e";

    public static Connection getConnection() {
        try {
            // Memastikan driver dimuatkan
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch(ClassNotFoundException e) {
            System.err.println("PostgreSQL Driver tidak dijumpai! Pastikan library ada dalam WEB-INF/lib atau pom.xml");
            e.printStackTrace();
            return null;
        } catch(SQLException e) {
            System.err.println("Gagal menyambung ke Database Render!");
            e.printStackTrace();
            return null;
        }
    }
}