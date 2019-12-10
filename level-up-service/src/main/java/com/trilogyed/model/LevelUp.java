package com.trilogyed.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "level_up")
public class LevelUp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "level_up_id", unique = true, nullable = false)
    private Integer Id;
    @Column(name = "customer_id", nullable = false)
    private Integer customerId;
    @Column(name = "points", nullable = false)
    private Integer points;
    @Column(name = "member_date", nullable = false)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate memberDate;

    public LevelUp() {
    }

    public LevelUp(Integer customerId, Integer points, LocalDate memberDate) {
        this.customerId = customerId;
        this.points = points;
        this.memberDate = memberDate;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
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
        return "LevelUp{" +
                "Id=" + Id +
                ", customerId=" + customerId +
                ", points=" + points +
                ", memberDate=" + memberDate +
                '}';
    }
}