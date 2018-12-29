package com.glhd.tb.app.base.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by chenliangj2ee on 2018/5/3.
 */

public class BeanInspSearchBaseData implements Serializable {

    private ArrayList<BeanSpinner> stations = new ArrayList<>();
    private ArrayList<BeanSpinner> types = new ArrayList<>();
    private ArrayList<BeanSpinner> locations = new ArrayList<>();
    private ArrayList<BeanSpinner> directions = new ArrayList<>();
    private ArrayList<BeanSpinner> carno = new ArrayList<>();
    private ArrayList<BeanSpinner> marshalling = new ArrayList<>();

    public ArrayList<BeanSpinner> getCarno() {
        return carno;
    }

    public void setCarno(ArrayList<BeanSpinner> carno) {
        this.carno = carno;
    }

    public ArrayList<BeanSpinner> getMarshalling() {
        return marshalling;
    }

    public void setMarshalling(ArrayList<BeanSpinner> marshalling) {
        this.marshalling = marshalling;
    }

    public ArrayList<BeanSpinner> getStations() {
        return stations;
    }

    public void setStations(ArrayList<BeanSpinner> stations) {
        this.stations = stations;
    }

    public ArrayList<BeanSpinner> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<BeanSpinner> types) {
        this.types = types;
    }

    public ArrayList<BeanSpinner> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<BeanSpinner> locations) {
        this.locations = locations;
    }

    public ArrayList<BeanSpinner> getDirections() {
        return directions;
    }

    public void setDirections(ArrayList<BeanSpinner> directions) {
        this.directions = directions;
    }
}
