package com.trilogyed.viewmodel;

import java.math.BigDecimal;
import java.util.Objects;

public class InvoiceItemViewModel {

    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private int quantityToPurchase;


    public InvoiceItemViewModel() {
    }

    public InvoiceItemViewModel(String productName, String productDescription, BigDecimal productPrice, int quantityToPurchase) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.quantityToPurchase = quantityToPurchase;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public int getQuantityToPurchase() {
        return quantityToPurchase;
    }

    public void setQuantityToPurchase(int quantityToPurchase) {
        this.quantityToPurchase = quantityToPurchase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvoiceItemViewModel)) return false;
        InvoiceItemViewModel that = (InvoiceItemViewModel) o;
        return getQuantityToPurchase() == that.getQuantityToPurchase() &&
                Objects.equals(getProductName(), that.getProductName()) &&
                Objects.equals(getProductDescription(), that.getProductDescription()) &&
                Objects.equals(getProductPrice(), that.getProductPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductName(), getProductDescription(), getProductPrice(), getQuantityToPurchase());
    }

    @Override
    public String toString() {
        return "InvoiceItemViewModel{" +
                "productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productPrice=" + productPrice +
                ", quantityToPurchase=" + quantityToPurchase +
                '}';
    }
}
