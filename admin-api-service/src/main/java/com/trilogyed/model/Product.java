package com.trilogyed.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    private Integer id;
    private String name;
    private String description;
    private BigDecimal listPrice;
    private BigDecimal unitCost;
    private Integer inventory;

    public Product() {
    }

    public Product(String name, String description, BigDecimal listPrice, BigDecimal unitCost, Integer inventory) {
        this.name = name;
        this.description = description;
        this.listPrice = listPrice;
        this.unitCost = unitCost;
        this.inventory = inventory;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                Objects.equals(listPrice, product.listPrice) &&
                Objects.equals(unitCost, product.unitCost) &&
                Objects.equals(inventory, product.inventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, listPrice, unitCost, inventory);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", listPrice=" + listPrice +
                ", unitCost=" + unitCost +
                ", inventory=" + inventory +
                '}';
    }
}