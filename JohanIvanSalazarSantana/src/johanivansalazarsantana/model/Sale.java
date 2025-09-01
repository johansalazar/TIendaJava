/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package johanivansalazarsantana.model;

import java.time.LocalDateTime;

/**
 *
 * @author JohanIvánSalazarSant
 */
public class Sale {

    private int id;
    private int productId;
    private int quantity;
    private double unitFinalPrice;
    private LocalDateTime saleDate;

    public Sale(int productId, int quantity, double unitFinalPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.unitFinalPrice = unitFinalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitFinalPrice() {
        return unitFinalPrice;
    }

    public void setUnitFinalPrice(double unitFinalPrice) {
        this.unitFinalPrice = unitFinalPrice;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }
}
