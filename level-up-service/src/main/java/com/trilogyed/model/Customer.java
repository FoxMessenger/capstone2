package com.trilogyed.model;

import java.util.Objects;

public class Customer {

    private Integer id;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String zip;
    private String email;
    private String phone;


    public Customer() {
        this("Jane", "Doe");
    }

    public Customer(String firstName, String lastName) {
        this("123 Main St", "Springfield", "33405", "jane@doe.edu", "8005882300");
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Customer(String street, String city, String zip, String email, String phone) {
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.email = email;
        this.phone = phone;
    }

    public Customer(String firstName, String lastName, String street, String city, String zip, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.email = email;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(getId(), customer.getId()) &&
                Objects.equals(getFirstName(), customer.getFirstName()) &&
                Objects.equals(getLastName(), customer.getLastName()) &&
                Objects.equals(getStreet(), customer.getStreet()) &&
                Objects.equals(getCity(), customer.getCity()) &&
                Objects.equals(getZip(), customer.getZip()) &&
                Objects.equals(getEmail(), customer.getEmail()) &&
                Objects.equals(getPhone(), customer.getPhone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getStreet(), getCity(), getZip(), getEmail(), getPhone());
    }

    @Override
    public String toString() {
        return "Customer Name: " + firstName + " " + lastName +
                " Address: " + street + " " + city + " " + zip +
                " Email: " + email +
                " Phone: " + phone;
    }
}
