/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.rh.config;

/**
 *
 * @author JoelDeAriovaldo
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static String url = "jdbc:mysql://localhost:3306/employee_db";
    private static String username = "admin_employee";
    private static String password = "admin123";

    public static Connection getConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Conexão com o banco de dados estabelecida com sucesso.");
            return connection;
        } catch (SQLException e) {
            System.err.println("Falha ao estabelecer conexão com o banco de dados.");
            e.printStackTrace();
            throw e;
        }
    }
}