/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package johanivansalazarsantana.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author JohanIvánSalazarSant
 */
public class StatsDAO {

    public double ingresos() throws SQLException {
        String sql = "SELECT COALESCE(SUM(total_value),0) FROM dbo.Sale";
        try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getDouble(1);
        }
    }

    public String masVendido() throws SQLException {
        String sql = "SELECT TOP 1 p.name FROM dbo.Sale s "
                + "JOIN dbo.Product p ON p.id = s.product_id "
                + "GROUP BY p.name "
                + "ORDER BY SUM(s.quantity) DESC, p.name ASC";
        try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString(1);
            }
            return "";
        }
    }

    public String menosVendido() throws SQLException {
        String sql = "SELECT TOP 1 p.name FROM dbo.Product p "
                + "LEFT JOIN (SELECT product_id, SUM(quantity) q FROM dbo.Sale GROUP BY product_id) s "
                + "ON s.product_id = p.id "
                + "GROUP BY p.name, s.q "
                + "ORDER BY COALESCE(s.q,0) ASC, p.name ASC";
        try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString(1);
            }
            return "";
        }
    }

    public double promedio() throws SQLException {
        String sql = "SELECT CASE WHEN SUM(quantity)=0 THEN 0 ELSE SUM(total_value)/SUM(quantity) END FROM dbo.Sale";
        try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getDouble(1);
        }
    }

    public Map<String, Integer> ventasPorProducto() throws SQLException {
        Map<String, Integer> map = new HashMap<>();
        String sql = "SELECT p.name as nombre , SUM(s.quantity) as total FROM Sale s JOIN Product p ON s.product_id = p.id \n"
                + "GROUP BY p.name";
        try (Connection conn = DB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getString("nombre"), rs.getInt("total"));
            }
        }
        return map;
    }

}
