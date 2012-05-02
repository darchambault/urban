package com.urban.simengine.structures;

import junit.framework.TestCase;

import java.awt.*;

public class BasicStructureTest extends TestCase {
    public void testConstructor() {
        BasicStructure structure = new BasicStructure(new Point(1, 2), new Dimension(3, 4));

        assertEquals(1.0, structure.getPosition().getX());
        assertEquals(2.0, structure.getPosition().getY());
        assertEquals(3.0, structure.getDimension().getWidth());
        assertEquals(4.0, structure.getDimension().getHeight());
    }
}
