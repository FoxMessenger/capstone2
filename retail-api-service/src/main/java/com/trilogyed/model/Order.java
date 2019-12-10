package com.trilogyed.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

public class Order {

    private String email;
    private Map<Integer, Integer> productAndQuantity;
    private LocalDate purchaseDate;

    public Order() {
    }

    public Order(String email, Map<Integer, Integer> productAndQuantity, LocalDate purchaseDate) {
        this.email = email;
        this.productAndQuantity = productAndQuantity;
        this.purchaseDate = purchaseDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<Integer, Integer> getProductAndQuantity() {
        return productAndQuantity;
    }

    public void setProductAndQuantity(Map<Integer, Integer> productAndQuantity) {
        this.productAndQuantity = productAndQuantity;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Objects.equals(email, order.email) &&
                Objects.equals(productAndQuantity, order.productAndQuantity) &&
                Objects.equals(purchaseDate, order.purchaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, productAndQuantity, purchaseDate);
    }

    @Override
    public String toString() {
        return "Order{" +
                "email='" + email + '\'' +
                ", productAndQuantity=" + productAndQuantity +
                ", purchaseDate=" + purchaseDate +
                '}';
    }
}
