package com.urban.simengine.managers.time.events;

import com.urban.simengine.agents.HumanAgent;

import java.util.GregorianCalendar;

public class TimeTickEventImpl implements TimeTickEvent {
    private GregorianCalendar time;

    public TimeTickEventImpl(GregorianCalendar time) {
        this.time = time;
    }

    public GregorianCalendar getDate() {
        return this.time;
    }
}
