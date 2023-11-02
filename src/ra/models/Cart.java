package ra.models;

import java.io.Serializable;

public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;
    private int cartId;
    private Product product;
    private int quantity;

    public Cart() {
    }

    public Cart(int cartId, Product product, int quantity) {
        this.cartId = cartId;
        this.product = product;
        this.quantity = quantity;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void display() {
        System.out.println("Id : " + cartId + "- Sản phẩm " + product.getProductName() + " - Số lượng " + quantity + " - Giá " + product.getPrice() + "x" + quantity + "= " + (quantity * product.getPrice()));
    }
}
