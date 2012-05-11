package com.urban.simengine.structures;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;
import org.easymock.IMocksControl;

import com.urban.simengine.Family;

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
        assertEquals(2, residence.getMaximumFamilies());
        assertEquals(0, residence.getFamilies().size());

        verify(pointMock, dimensionMock);
    }

    @Test public void testConstructorWithFamilies() {
        IMocksControl control = createControl();

        Point pointMock = control.createMock(Point.class);
        Dimension dimensionMock = control.createMock(Dimension.class);

        Family familyMock1 = control.createMock(Family.class);
        Family familyMock2 = control.createMock(Family.class);
        Set<Family> families = new HashSet<Family>();
        families.add(familyMock1);
        families.add(familyMock2);

        control.replay();

        ResidenceStructure residence = new ResidenceStructureImpl(pointMock, dimensionMock, 2, families);

        assertSame(pointMock, residence.getPosition());
        assertSame(dimensionMock, residence.getDimension());
        assertEquals(2, residence.getMaximumFamilies());
        assertEquals(2, residence.getFamilies().size());
        assertTrue(residence.getFamilies().contains(familyMock1));
        assertTrue(residence.getFamilies().contains(familyMock2));

        control.verify();
    }
}
