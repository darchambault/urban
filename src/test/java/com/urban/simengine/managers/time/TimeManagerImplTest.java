package com.urban.simengine.managers.time;

import com.google.common.eventbus.EventBus;
import com.urban.simengine.managers.time.events.TimeTickEvent;
import org.easymock.IMocksControl;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeManagerImplTest  {
    @Test public void testConstructor() {
        int tickLengthUnit = Calendar.DAY_OF_MONTH;
        int tickLength = 1;

        IMocksControl control = createControl();

        GregorianCalendar startDateMock = control.createMock(GregorianCalendar.class);
        GregorianCalendar startDateCloneMock = control.createMock(GregorianCalendar.class);
        expect(startDateMock.clone()).andReturn(startDateCloneMock).once();

        EventBus eventBusMock = control.createMock(EventBus.class);

        control.replay();

        TimeManagerImpl manager = new TimeManagerImpl(startDateMock, tickLengthUnit, tickLength, eventBusMock);

        assertSame(startDateMock, manager.getStartDate());
        assertEquals(tickLengthUnit, manager.getTickLengthUnit());
        assertEquals(tickLength, manager.getTickLength());
        assertNotSame(startDateMock, manager.getCurrentDate());
        assertSame(startDateCloneMock, manager.getCurrentDate());

        control.verify();
    }

    @Test public void testTick() {
        IMocksControl control = createControl();

        GregorianCalendar startDateMock = control.createMock(GregorianCalendar.class);
        GregorianCalendar startDateCloneMock = control.createMock(GregorianCalendar.class);
        expect(startDateMock.clone()).andReturn(startDateCloneMock).once();
        GregorianCalendar currentDateCloneMock = control.createMock(GregorianCalendar.class);
        expect(startDateCloneMock.clone()).andReturn(currentDateCloneMock).once();

        startDateCloneMock.add(Calendar.DAY_OF_MONTH, 1);

        EventBus eventBusMock = control.createMock(EventBus.class);
        eventBusMock.post(anyObject(TimeTickEvent.class));
        expectLastCall().once();

        control.replay();

        TimeManagerImpl manager = new TimeManagerImpl(startDateMock, Calendar.DAY_OF_MONTH, 1, eventBusMock);
        GregorianCalendar newDate = manager.tick();

        assertEquals(startDateCloneMock, newDate);

        control.verify();
    }
}
