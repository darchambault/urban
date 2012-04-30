package com.urban.simengine.structures;
import java.awt.Dimension;
import java.awt.Point;

public class BasicStructure {
    private Point position;
    private Dimension dimension;

    public BasicStructure(Point position, Dimension dimension) {
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
