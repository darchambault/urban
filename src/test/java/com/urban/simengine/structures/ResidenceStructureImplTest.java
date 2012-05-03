package com.urban.simengine.structures;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;
import org.easymock.IMocksControl;

import com.urban.simengine.agents.HumanAgent;

import java.awt.*;
import java.util.*;


public class ResidenceStructureImplTest  {
    @Test public void testConstructorWithoutResidents() {
        Point pointMock = createMock(Point.class);
        Dimension dimensionMock = createMock(Dimension.class);

        replay(pointMock, dimensionMock);

        ResidenceStructure residence = new ResidenceStructureImpl(pointMock, dimensionMock, 2);

        assertSame(pointMock, residence.getPosition());
        assertSame(dimensionMock, residence.getDimension());
        assertEquals(2, residence.getMaximumResidents());
        assertEquals(0, residence.getResidents().size());

        verify(pointMock, dimensionMock);
    }

    @Test public void testConstructorWithResidents() {
        IMocksControl control = createControl();

        Point pointMock = control.createMock(Point.class);
        Dimension dimensionMock = control.createMock(Dimension.class);

        HumanAgent humanMock1 = control.createMock(HumanAgent.class);
        HumanAgent humanMock2 = control.createMock(HumanAgent.class);
        Set<HumanAgent> residents = new HashSet<HumanAgent>();
        residents.add(humanMock1);
        residents.add(humanMock2);

        control.replay();

        ResidenceStructure residence = new ResidenceStructureImpl(pointMock, dimensionMock, 2, residents);

        assertSame(pointMock, residence.getPosition());
        assertSame(dimensionMock, residence.getDimension());
        assertEquals(2, residence.getMaximumResidents());
        assertEquals(2, residence.getResidents().size());
        assertTrue(residence.getResidents().contains(humanMock1));
        assertTrue(residence.getResidents().contains(humanMock2));

        control.verify();
    }
}
