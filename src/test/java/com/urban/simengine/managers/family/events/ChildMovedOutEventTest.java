package com.urban.simengine.managers.family.events;

import com.urban.simengine.agents.HumanAgent;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertSame;

public class ChildMovedOutEventTest {
    @Test public void testConstructor() {
        HumanAgent humanMock = createMock(HumanAgent.class);

        replay(humanMock);

        ChildMovedOutEvent event = new ChildMovedOutEventImpl(humanMock);

        assertSame(humanMock, event.getHuman());

        verify(humanMock);
    }
}
