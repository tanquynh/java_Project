package ra.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ra.constant.Contant.Inportance.OPEN;
import static ra.constant.Contant.Role.ADMIN;
import static ra.constant.Contant.Status.INACTIVE;

public class User implements Serializable {
    private int id;
    private String username;
    private String fullName;
    private String password;
    private String email;
    private String phone;
    private boolean status;
    private boolean importance;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String address;
    private int role;
    private List<Cart> cart = new ArrayList<>();

    public User() {
        status = INACTIVE;
        importance = OPEN;
    }

    public User(int id, String username, String fullName, String password, String email, String phone, boolean status, boolean importance, LocalDate createdAt, LocalDate updatedAt, String address, int role, List<Cart> cart) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.importance = importance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.address = address;
        this.role = role;
        this.cart = cart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isStatus() {
        return status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isImportance() {
        return importance;
    }

    public void setImportance(boolean importance) {
        this.importance = importance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public List<Cart> getCart() {
        return cart;
    }

    public void setCart(List<Cart> cart) {
        this.cart = cart;
    }

    public void display() {
        System.out.println("ID:" + this.id + " -Username: " + this.username + " - Email: " + this.email);
        System.out.println("Status: " + (this.status ? "ACTIVE" : "INACTIVE") + " - Role: " + (this.role == ADMIN ? "ADMIN" : "USER") + " -Trạng thái khóa/mở:" + (this.importance == OPEN ? "MỞ" : "KHÓA"));
        System.out.println("Thời gian tạo: " + this.createdAt + " -- Thời gian cập nhật: " +(this.updatedAt == null ? "Chưa cập nhật" : this.updatedAt) );
        System.out.println("|------------------------------------------------------|");
    }
}
