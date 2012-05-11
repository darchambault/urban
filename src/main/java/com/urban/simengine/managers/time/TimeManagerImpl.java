package com.urban.simengine.managers.time;

import com.google.common.eventbus.EventBus;
import com.urban.simengine.managers.time.events.TimeTickEventImpl;

import java.util.Calendar;

public class TimeManagerImpl implements TimeManager {
    private EventBus eventBus;
    private Calendar startDate;
    private int tickLengthUnit;
    private int tickLength;
    private Calendar currentDate;

    public TimeManagerImpl(Calendar startDate, int tickLengthUnit, int tickLength, EventBus eventBus) {
        this.startDate = startDate;
        this.tickLengthUnit = tickLengthUnit;
        this.tickLength = tickLength;
        this.currentDate = (Calendar) startDate.clone();
        this.eventBus = eventBus;
    }

    public Calendar getStartDate() {
        return this.startDate;
    }

    public int getTickLengthUnit() {
        return this.tickLengthUnit;
    }

    public int getTickLength() {
        return this.tickLength;
    }

    public Calendar getCurrentDate() {
        return this.currentDate;
    }

    public Calendar tick() {
        this.currentDate.add(this.tickLengthUnit, this.tickLength);
        Calendar dateClone = (Calendar) this.currentDate.clone();
        this.eventBus.post(new TimeTickEventImpl(dateClone));
        return this.currentDate;
    }
}
