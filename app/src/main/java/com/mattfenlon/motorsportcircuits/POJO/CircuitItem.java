package com.mattfenlon.motorsportcircuits.POJO;


/**
 * Created by Matt Fenlon on 31/12/15.
 */
public class CircuitItem {

    int lat, lon;
    String name;

    public CircuitItem(int lat, int lon, String name) {

        this.lat = lat;
        this.lon = lon;
        this.name = name;
    }

    public int getLat() {

        return lat;
    }

    public void setLat(int lat) {

        this.lat = lat;
    }

    public int getLon() {

        return lon;
    }

    public void setLon(int lon) {

        this.lon = lon;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }
}
