package com.urban.simengine.managers.population.events;

import org.junit.Test;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertSame;

import com.urban.simengine.agents.HumanAgent;


public class BirthEventTest {
    @Test public void testConstructor() {
        HumanAgent humanMock = createMock(HumanAgent.class);

        replay(humanMock);

        BirthEvent event = new BirthEventImpl(humanMock);

        assertSame(humanMock, event.getHuman());

        verify(humanMock);
    }
}
