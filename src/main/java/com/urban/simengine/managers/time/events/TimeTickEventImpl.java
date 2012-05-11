package com.urban.simengine.managers.time.events;

import java.util.Calendar;

public class TimeTickEventImpl implements TimeTickEvent {
    private Calendar time;

    public TimeTickEventImpl(Calendar time) {
        this.time = time;
    }

    public Calendar getDate() {
        return this.time;
    }
}
