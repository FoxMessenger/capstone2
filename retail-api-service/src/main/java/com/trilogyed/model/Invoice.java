package com.trilogyed.model;

import java.time.LocalDate;
import java.util.Objects;

public class Invoice {
    private int id;
    private int customerId;
    private LocalDate purchaseDate;

    public Invoice() {
    }

    public Invoice(int customerId, LocalDate purchaseDate) {
        this.customerId = customerId;
        this.purchaseDate = purchaseDate;
    }

    public Invoice(int id, int customerId, LocalDate purchaseDate) {
        this.id = id;
        this.customerId = customerId;
        this.purchaseDate = purchaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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
        if (!(o instanceof Invoice)) return false;
        Invoice invoice = (Invoice) o;
        return getId() == invoice.getId() &&
                getCustomerId() == invoice.getCustomerId() &&
                Objects.equals(getPurchaseDate(), invoice.getPurchaseDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCustomerId(), getPurchaseDate());
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", purchaseDate=" + purchaseDate +
                '}';
    }
}
