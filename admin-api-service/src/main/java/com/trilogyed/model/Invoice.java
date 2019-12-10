package com.trilogyed.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.Objects;

public class Invoice {

    private Integer id;
    private int customerId;
    private LocalDate purchaseDate;

    public Invoice() {
    }

    public Invoice(int customerId, LocalDate purchaseDate) {
        this.customerId = customerId;
        this.purchaseDate = purchaseDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return customerId == invoice.customerId &&
                Objects.equals(purchaseDate, invoice.purchaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, purchaseDate);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "customerId=" + customerId +
                ", purchaseDate=" + purchaseDate +
                '}';
    }
}
