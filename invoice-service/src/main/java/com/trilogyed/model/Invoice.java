package com.trilogyed.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "customer_id", nullable = false)
    private int customerId;
    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    public Invoice() {
    }

    public Invoice(int customerId, LocalDate purchaseDate) {
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
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return id == invoice.id &&
                customerId == invoice.customerId &&
                Objects.equals(purchaseDate, invoice.purchaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, purchaseDate);
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
