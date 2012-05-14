package com.urban.simengine.managers.family.events;

import org.junit.Test;
import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import com.urban.simengine.Family;

public class FamilyMovedInEventTest {
    @Test public void testConstructor() {
        Family familyMock = createMock(Family.class);

        replay(familyMock);

        FamilyMovedInEvent event = new FamilyMovedInEventImpl(familyMock);

        assertThat(event.getFamily(), sameInstance(familyMock));

        verify(familyMock);
    }
}
