/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package johanivansalazarsantana.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import johanivansalazarsantana.model.Product;

/**
 *
 * @author JohanIvánSalazarSant
 */
public class ProductDAO {

    public List<Product> findAll() throws SQLException {
        String sql = "SELECT id,name,type,stock,min_stock,base_price,tax_rate,reorder_qty FROM dbo.Product ORDER BY id";
        try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Product> list = new ArrayList<>();
            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getInt("stock"),
                        rs.getInt("min_stock"),
                        rs.getDouble("base_price"),
                        rs.getDouble("tax_rate"),
                        rs.getInt("reorder_qty"));
                list.add(p);
            }
            return list;
        }
    }

    public Product findById(int id) throws SQLException {
        String sql = "SELECT id,name,type,stock,min_stock,base_price,tax_rate,reorder_qty FROM dbo.Product WHERE id=?";
        try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("type"),
                            rs.getInt("stock"),
                            rs.getInt("min_stock"),
                            rs.getDouble("base_price"),
                            rs.getDouble("tax_rate"),
                            rs.getInt("reorder_qty"));
                }
                return null;
            }
        }
    }

    public void updateStock(int productId, int newStock) throws SQLException {
        String sql = "UPDATE dbo.Product SET stock=? WHERE id=?";
        try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, newStock);
            ps.setInt(2, productId);
            ps.executeUpdate();
        }
    }
}
