/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package johanivansalazarsantana.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author JohanIvánSalazarSant
 */
public class DB {

    private static final String URL = "jdbc:sqlserver://DESKTOP-UPMOAKB;databaseName=tienda;encrypt=false;trustServerCertificate=true;";
    private static final String USER = "smartadmin";
    private static final String PASS = "smartadmin";

    public static Connection getConnection() throws SQLException {
        String url = URL;
        String user = USER;
        String pass = PASS;
          try {
            // Registrar el driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Conectar
            Connection conn = DriverManager.getConnection(url, user, pass);         

        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
         
            
        return DriverManager.getConnection(url, user, pass);
    }

    /*public static int defaultReorderQty() {
        return Integer.parseInt(props.getProperty("reorder.qty", "20"));
    }*/
}
