package com.urban.simengine.structures;

import java.awt.*;

public class BasicStructureImpl implements BasicStructure {
    private Point position;
    private Dimension dimension;

    public BasicStructureImpl(Point position, Dimension dimension) {
        this.position = position;
        this.dimension = dimension;
    }

    public Point getPosition() {
        return this.position;
    }

    public Dimension getDimension() {
        return this.dimension;
    }
}
