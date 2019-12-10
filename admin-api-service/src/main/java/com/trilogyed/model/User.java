package com.trilogyed.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    @Column(name = "username", nullable = false)
    private String userName;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "enabled", nullable = false)
    private boolean isEnabled;

    // One to many
    // One to One 
    public User() {
    }

    public User(String userName, String password, boolean isEnabled) {
        this.userName = userName;
        this.password = password;
        this.isEnabled = isEnabled;
    }

    public User(Integer Id, String userName, String password, boolean isEnabled) {
        this.Id = Id;
        this.userName = userName;
        this.password = password;
        this.isEnabled = isEnabled;
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

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public boolean getisEnabled() {
        return isEnabled;
    }

    public void setisEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}