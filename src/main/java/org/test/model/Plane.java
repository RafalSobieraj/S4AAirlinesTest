package org.test.model;

import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@EqualsAndHashCode
public class Plane {

    private int id;
    private int lastChangeDay;
    private int startingDayNumber;
    private LinkedHashMap<Integer, Integer> maxPassengersWithDays;
    private boolean active;

    public Plane(int id, LinkedHashMap<Integer, Integer> maxPassengersWithDays) {
        this.id = id;
        this.maxPassengersWithDays = maxPassengersWithDays;
        this.active = true;
        this.lastChangeDay = 0;
        this.startingDayNumber = 0;
    }
}
