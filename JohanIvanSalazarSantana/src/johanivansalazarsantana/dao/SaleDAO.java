/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package johanivansalazarsantana.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import johanivansalazarsantana.model.Sale;
/**
 *
 * @author JohanIvánSalazarSant
 */
public class SaleDAO {

    public void create(Sale s) throws SQLException {
        String sql = "INSERT INTO dbo.Sale(product_id,quantity,unit_final_price) VALUES(?,?,?)";
        try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, s.getProductId());
            ps.setInt(2, s.getQuantity());
            ps.setDouble(3, s.getUnitFinalPrice());
            ps.executeUpdate();
        }
    }
}
