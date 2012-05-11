package com.urban.simengine.managers.family.events;

import org.junit.Test;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertSame;

import com.urban.simengine.Family;

public class CoupleCreatedEventTest {
    @Test public void testConstructor() {
        Family familyMock = createMock(Family.class);

        replay(familyMock);

        CoupleCreatedEvent event = new CoupleCreatedEventImpl(familyMock);

        assertSame(familyMock, event.getFamily());

        verify(familyMock);
    }
}
