package com.example.httplearningapi.model.entities.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@SuppressWarnings("unused")
@Entity
@Table(name = "Geos")
@JsonAutoDetect
public class Geo {

    @Id
    @JsonIgnore
    private int id;
    private float lat;
    private float lon;

    public Geo() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Geo geo = (Geo) o;
        return Float.compare(lat, geo.lat) == 0 && Float.compare(lon, geo.lon) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lon);
    }

    @Override
    public String toString() {
        return "Geo{" +
                "id=" + id +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
