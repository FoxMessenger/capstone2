package com.trilogyed.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    private Integer Id;
    private String name;
    private String description;
    private BigDecimal listPrice;
    private BigDecimal unitCost;
    private Integer inventory;

    public Product() {
        this("Hairspray", "Momma told me not to use it.", BigDecimal.valueOf(12.99), BigDecimal.valueOf(4.75), 100);
    }

    public Product(String name, String description, BigDecimal listPrice, BigDecimal unitCost, Integer inventory) {
        this.name = name;
        this.description = description;
        this.listPrice = listPrice;
        this.unitCost = unitCost;
        this.inventory = inventory;
    }

    public Product(Integer id) {
        Id = id;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(getId(), product.getId()) &&
                Objects.equals(getName(), product.getName()) &&
                Objects.equals(getDescription(), product.getDescription()) &&
                Objects.equals(getListPrice(), product.getListPrice()) &&
                Objects.equals(getUnitCost(), product.getUnitCost()) &&
                Objects.equals(getInventory(), product.getInventory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getListPrice(), getUnitCost(), getInventory());
    }

    @Override
    public String toString() {
        return "Product{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", listPrice=" + listPrice +
                ", unitCost=" + unitCost +
                ", inventory=" + inventory +
                '}';
    }
}
