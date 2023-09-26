package com.example.httplearningapi.model.user;

import jakarta.persistence.*;

@Entity
@Table(name = "Addresses")
public class Address {

    @Id
    private String country;

    private String city;
    private String street;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Geo geo;
    public Address() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }
}
