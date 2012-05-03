package com.urban.simengine.managers.time;

import java.util.GregorianCalendar;

public interface TimeManager {
    public GregorianCalendar getStartDate();

    public int getTickLengthUnit();

    public int getTickLength();

    public GregorianCalendar getCurrentDate();

    public GregorianCalendar tick();
}
