package com.example.httplearningapi.model.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity @Table(name = "Geos")
@JsonAutoDetect
public class Geo {

    @Id
    @JsonIgnore
    private int id;

    private float lat;
    private float lon;

    public Geo() {
    }

    @Override
    public String toString() {
        return "Geo{" +
                "id=" + id +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }
}
