package com.trilogyed.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "invoice_item")
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "invoice_id", nullable = false)
    private int invoiceId;
    @Column(name = "product_id", nullable = false)
    private int productId;
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    public InvoiceItem() {
    }

    public InvoiceItem(int invoiceId, int productId, int quantity, BigDecimal unitPrice) {
        this.invoiceId = invoiceId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
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

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceItem that = (InvoiceItem) o;
        return id == that.id &&
                invoiceId == that.invoiceId &&
                productId == that.productId &&
                quantity == that.quantity &&
                Objects.equals(unitPrice, that.unitPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, invoiceId, productId, quantity, unitPrice);
    }

    @Override
    public String toString() {
        return "InvoiceItem{" +
                "id=" + id +
                ", invoiceId=" + invoiceId +
                ", inventoryId=" + productId +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
