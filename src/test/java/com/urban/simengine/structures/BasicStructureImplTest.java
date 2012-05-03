package com.urban.simengine.structures;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import java.awt.*;

public class BasicStructureImplTest  {
    @Test public void testConstructor() {
        Point pointMock = createMock(Point.class);
        Dimension dimensionMock = createMock(Dimension.class);

        replay(pointMock, dimensionMock);

        BasicStructureImpl structure = new BasicStructureImpl(pointMock, dimensionMock);

        assertSame(pointMock, structure.getPosition());
        assertSame(dimensionMock, structure.getDimension());

        verify(pointMock, dimensionMock);
    }
}
