package com.urban.simengine.managers.time;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeManagerImplTest  {
    @Test public void testConstructor() {
        int tickLengthUnit = Calendar.DAY_OF_MONTH;
        int tickLength = 1;

        GregorianCalendar startDateMock = createMock(GregorianCalendar.class);
        GregorianCalendar startDateCloneMock = createMock(GregorianCalendar.class);
        expect(startDateMock.clone()).andReturn(startDateCloneMock).once();

        replay(startDateMock, startDateCloneMock);

        TimeManagerImpl manager = new TimeManagerImpl(startDateMock, tickLengthUnit, tickLength);

        assertSame(startDateMock, manager.getStartDate());
        assertEquals(tickLengthUnit, manager.getTickLengthUnit());
        assertEquals(tickLength, manager.getTickLength());
        assertNotSame(startDateMock, manager.getCurrentDate());
        assertSame(startDateCloneMock, manager.getCurrentDate());

        verify(startDateMock, startDateCloneMock);
    }

    @Test public void testTick() {
        GregorianCalendar startDateMock = createMock(GregorianCalendar.class);
        GregorianCalendar startDateCloneMock = createMock(GregorianCalendar.class);
        expect(startDateMock.clone()).andReturn(startDateCloneMock).once();

        startDateCloneMock.add(5, 1);

        replay(startDateMock, startDateCloneMock);

        TimeManagerImpl manager = new TimeManagerImpl(startDateMock, Calendar.DAY_OF_MONTH, 1);
        GregorianCalendar newDate = manager.tick();

        assertEquals(startDateCloneMock, newDate);

        verify(startDateMock, startDateCloneMock);
    }
}
