/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author JohanIvánSalazarSant
 */
public class test {

    public void test() {
        String url = "jdbc:sqlserver://DESKTOP-UPMOAKB;databaseName=tienda;encrypt=false;trustServerCertificate=true;";
        String user = "smartadmin";
        String pass = "smartadmin";

        try {
            // Registrar el driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Conectar
            Connection conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Conexion exitosa a la base de datos");

        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }
}
