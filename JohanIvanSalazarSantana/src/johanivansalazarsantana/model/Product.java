/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package johanivansalazarsantana.model;

/**
 *
 * @author JohanIvánSalazarSant
 */
public class Product {

    private int id;
    private String name;
    private String type;
    private int stock;
    private int minStock;
    private double basePrice;
    private double taxRate;
    private int reorderQty;

    public Product() {
    }

    public Product(int id, String name, String type, int stock, int minStock, double basePrice, double taxRate, int reorderQty) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.stock = stock;
        this.minStock = minStock;
        this.basePrice = basePrice;
        this.taxRate = taxRate;
        this.reorderQty = reorderQty;
    }

    public double finalPrice() {
        return basePrice * (1.0 + taxRate);
    }

    public boolean needsReorder() {
        return stock <= minStock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMinStock() {
        return minStock;
    }

    public void setMinStock(int minStock) {
        this.minStock = minStock;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public int getReorderQty() {
        return reorderQty;
    }

    public void setReorderQty(int reorderQty) {
        this.reorderQty = reorderQty;
    }
}
