/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package johanivansalazarsantana.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author JohanIvánSalazarSant
 */
public class OrderDAO {

    public void create(int productId, int qty) throws SQLException {
        String sql = "INSERT INTO dbo.[Order](product_id,quantity) VALUES(?,?)";
        try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ps.setInt(2, qty);
            ps.executeUpdate();
        }
    }
}
