package com.urban.simengine.managers.time;

import java.util.Calendar;

public interface TimeManager {
    public Calendar getStartDate();

    public int getTickLengthUnit();

    public int getTickLength();

    public Calendar getCurrentDate();

    public Calendar tick();
}
