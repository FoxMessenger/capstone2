package com.trilogyed.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "authorities")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    @Column(name = "username", nullable = false)
    private String userName;
    @Column(name = "authority", nullable = false)
    private String authority;

    // One to many
    // One to One 
    public Authority() {
    }

    public Authority(String userName, String authority) {
        this.userName = userName;
        this.authority = authority;
    }

    public Authority(Integer Id, String userName, String authority) {
        this.Id = Id;
        this.userName = userName;
        this.authority = authority;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getuserName() {
        return userName;
    }

    public void setuserName(String userName) {
        this.userName = userName;
    }

    public String getauthority() {
        return authority;
    }

    public void setauthority(String authority) {
        this.authority = authority;
    }
}