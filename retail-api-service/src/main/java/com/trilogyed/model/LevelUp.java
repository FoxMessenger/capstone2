package com.trilogyed.model;

import java.time.LocalDate;
import java.util.Objects;

public class LevelUp {

    private Integer Id;
    private Integer customerId;
    private Integer points;
    private LocalDate memberDate;

    public LevelUp() {
        this(1, 150, LocalDate.of(2018, 1, 30));
    }

    public LevelUp(Integer customerId, Integer points, LocalDate memberDate) {
        this.customerId = customerId;
        this.points = points;
        this.memberDate = memberDate;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public LocalDate getMemberDate() {
        return memberDate;
    }

    public void setMemberDate(LocalDate memberDate) {
        this.memberDate = memberDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LevelUp)) return false;
        LevelUp levelUp = (LevelUp) o;
        return Objects.equals(getId(), levelUp.getId()) &&
                Objects.equals(getCustomerId(), levelUp.getCustomerId()) &&
                Objects.equals(getPoints(), levelUp.getPoints()) &&
                Objects.equals(getMemberDate(), levelUp.getMemberDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCustomerId(), getPoints(), getMemberDate());
    }

    @Override
    public String toString() {
        return "LevelUp Points: " + points +
                " Member Since: " + memberDate;
    }
}
