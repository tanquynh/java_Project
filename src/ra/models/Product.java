package ra.models;

import java.io.Serializable;

import static ra.constant.Contant.Inportance.OPEN;
import static ra.constant.Contant.Role.ADMIN;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private int productId;
    private String productName;
    private double price;
    private int stock;
    private String productDes;
//    private String style;
    private Category category;
    private boolean productStatus;

    public Product() {
    }

    public Product(int productId, String productName, double price, int stock,String productDes,String style, Category category, boolean productStatus) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.stock = stock;
        this.productDes = productDes;
//        this.style = style;
        this.category = category;
        this.productStatus = productStatus;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getProductDes() {
        return productDes;
    }

    public void setProductDes(String style) {
        this.productDes = style;
    }
//
//    public String getStyle() {
//        return style;
//    }
//
//    public void setStyle(String style) {
//        this.style = style;
//    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isProductStatus() {
        return productStatus;
    }

    public void setProductStatus(boolean productStatus) {
        this.productStatus = productStatus;
    }

    public void setQuantity(int stock) {
        this.stock = stock;
    }

    public void display() {
        System.out.println("ID:" + this.productId + " -ProductName: " + this.productName + " -Giá: " + this.price  + " -Trạng thái ẩn/hiện:" + (this.productStatus == true ? "UnHide" : "Hide")   );
        System.out.println("|------------------------------------------------------|");
    }
}
