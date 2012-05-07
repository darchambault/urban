package com.urban.simengine.managers.time;

import com.google.common.eventbus.EventBus;
import com.urban.simengine.managers.time.events.TimeTickEventImpl;

import java.util.GregorianCalendar;

public class TimeManagerImpl implements TimeManager {
    private EventBus eventBus;
    private GregorianCalendar startDate;
    private int tickLengthUnit;
    private int tickLength;
    private GregorianCalendar currentDate;

    public TimeManagerImpl(GregorianCalendar startDate, int tickLengthUnit, int tickLength, EventBus eventBus) {
        this.startDate = startDate;
        this.tickLengthUnit = tickLengthUnit;
        this.tickLength = tickLength;
        this.currentDate = (GregorianCalendar) startDate.clone();
        this.eventBus = eventBus;
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
        this.currentDate.add(this.tickLengthUnit, this.tickLength);
        GregorianCalendar dateClone = (GregorianCalendar) this.currentDate.clone();
        this.eventBus.post(new TimeTickEventImpl(dateClone));
        return this.currentDate;
    }
}
