package com.urban.simengine;

import java.util.GregorianCalendar;

public class TimeManager {
    private GregorianCalendar startDate;
    private int tickLengthUnit;
    private int tickLength;
    private GregorianCalendar currentDate;

    public TimeManager(GregorianCalendar startDate, int tickLengthUnit, int tickLength) {
        this.startDate = startDate;
        this.tickLengthUnit = tickLengthUnit;
        this.tickLength = tickLength;
        this.currentDate = startDate;
        this.currentDate = (GregorianCalendar) startDate.clone();
    }

    public GregorianCalendar getStartDate() {
        return this.startDate;
    }

    public int getTickLengthUnit() {
        return this.tickLengthUnit;
    }

    public int getTickLength() {
        return this.tickLength;
    }

    public GregorianCalendar getCurrentDate() {
        return this.currentDate;
    }

    public GregorianCalendar tick() {
//        this.currentDate = new GregorianCalendar();
        this.currentDate.add(this.tickLengthUnit, this.tickLength);
        return this.currentDate;
    }
}
