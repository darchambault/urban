package com.urban.simengine.managers.time.events;

import org.junit.Test;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertSame;

import java.util.GregorianCalendar;

public class TimeTickEventTest {
    @Test public void testConstructor() {
        GregorianCalendar dateMock = createMock(GregorianCalendar.class);

        replay(dateMock);

        TimeTickEvent event = new TimeTickEventImpl(dateMock);

        assertSame(dateMock, event.getDate());

        verify(dateMock);
    }
}
