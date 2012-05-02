package com.urban.simengine;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeManagerTest extends TestCase {
    public void testConstructor() {
        GregorianCalendar startDate = new GregorianCalendar(2000, 1, 1);
        int tickLengthUnit = Calendar.DAY_OF_MONTH;
        int tickLength = 1;

        TimeManager manager = new TimeManager((GregorianCalendar) startDate.clone(), tickLengthUnit, tickLength);

        assertEquals(startDate, manager.getStartDate());
        assertEquals(tickLengthUnit, manager.getTickLengthUnit());
        assertEquals(tickLength, manager.getTickLength());
        assertEquals(startDate, manager.getCurrentDate());
    }

    public void testTick() {
        GregorianCalendar startDate = new GregorianCalendar(2000, 1, 1);

        TimeManager manager = new TimeManager((GregorianCalendar) startDate.clone(), Calendar.DAY_OF_MONTH, 1);

        GregorianCalendar newDate = manager.tick();

        GregorianCalendar expectedDate = new GregorianCalendar(2000, 1, 2);

        assertEquals(expectedDate, newDate);
        assertEquals(expectedDate, manager.getCurrentDate());
        assertEquals(startDate, manager.getStartDate());
    }
}
