package com.trilogyed.model;

import java.math.BigDecimal;
import java.util.Objects;

public class InvoiceItem {

    private int id;
    private int invoiceId;
    private int productId;
    private int quantity;
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
        if (!(o instanceof InvoiceItem)) return false;
        InvoiceItem that = (InvoiceItem) o;
        return getId() == that.getId() &&
                getInvoiceId() == that.getInvoiceId() &&
                getProductId() == that.getProductId() &&
                getQuantity() == that.getQuantity() &&
                Objects.equals(getUnitPrice(), that.getUnitPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getInvoiceId(), getProductId(), getQuantity(), getUnitPrice());
    }

    @Override
    public String toString() {
        return "InvoiceItem{" +
                "id=" + id +
                ", invoiceId=" + invoiceId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
