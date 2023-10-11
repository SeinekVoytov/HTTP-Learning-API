package com.example.httplearningapi.model.entities.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;

@SuppressWarnings("unused")
@Entity
@Table(name = "Addresses")
@JsonAutoDetect
public class Address {

    @Id
    @JsonIgnore
    private int id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(country, address.country) && Objects.equals(city, address.city) && Objects.equals(street, address.street) && Objects.equals(geo, address.geo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, street, geo);
    }

    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", geo=" + geo +
                '}';
    }
}
