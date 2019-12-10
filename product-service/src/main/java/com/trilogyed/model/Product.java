package com.trilogyed.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @NotEmpty
    @Column(name = "product_name", nullable = false)
    private String name;
    @NotEmpty
    @Column(name = "product_description", nullable = false)
    private String description;
    @NotNull
    @Positive
    @Column(name = "list_price", nullable = false)
    private BigDecimal listPrice;
    @NotNull
    @Positive
    @Column(name = "unit_cost", nullable = false)
    private BigDecimal unitCost;
    @NotNull
    @Positive
    @Column(name = "inventory", nullable = false)
    private Integer inventory;

    //@OneToMany(mappedBy = note_id, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //private Set<Note> notes;
    public Product() {
    }

    public Product(@NotEmpty String name, @NotEmpty String description, @NotEmpty @Positive BigDecimal listPrice, @NotEmpty @Positive BigDecimal unitCost, @NotEmpty @Positive Integer inventory) {
        this.name = name;
        this.description = description;
        this.listPrice = listPrice;
        this.unitCost = unitCost;
        this.inventory = inventory;
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
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(Id, product.Id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                Objects.equals(listPrice, product.listPrice) &&
                Objects.equals(unitCost, product.unitCost) &&
                Objects.equals(inventory, product.inventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, name, description, listPrice, unitCost, inventory);
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