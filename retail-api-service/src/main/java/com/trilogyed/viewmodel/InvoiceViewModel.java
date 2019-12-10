package com.trilogyed.viewmodel;

import com.trilogyed.model.LevelUp;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class InvoiceViewModel {

    private List<InvoiceItemViewModel> items;
    private CustomerViewModel customer;
    private Double total;
    private LocalDate purchaseDate;
    private LevelUp levelUp;


    public InvoiceViewModel() {
    }

    public InvoiceViewModel(CustomerViewModel customer, LevelUp levelUp) {
        this.customer = customer;
        this.levelUp = levelUp;
    }

    public List<InvoiceItemViewModel> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItemViewModel> items) {
        this.items = items;
    }

    public CustomerViewModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerViewModel customer) {
        this.customer = customer;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LevelUp getLevelUp() {
        return levelUp;
    }

    public void setLevelUp(LevelUp levelUp) {
        this.levelUp = levelUp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvoiceViewModel)) return false;
        InvoiceViewModel that = (InvoiceViewModel) o;
        return Objects.equals(getItems(), that.getItems()) &&
                Objects.equals(getCustomer(), that.getCustomer()) &&
                Objects.equals(getTotal(), that.getTotal()) &&
                Objects.equals(getPurchaseDate(), that.getPurchaseDate()) &&
                Objects.equals(getLevelUp(), that.getLevelUp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItems(), getCustomer(), getTotal(), getPurchaseDate(), getLevelUp());
    }

    @Override
    public String toString() {
        return "InvoiceViewModel{" +
                "items=" + items +
                ", customer=" + customer +
                ", total=" + total +
                ", purchaseDate=" + purchaseDate +
                ", levelUp=" + levelUp +
                '}';
    }
}
