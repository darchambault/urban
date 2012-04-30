package com.urban.simengine.structures;

import com.urban.simengine.WorkPosition;

import java.awt.Point;
import java.awt.Dimension;
import java.util.List;

public class WorkStructure extends BasicStructure {
    private List<WorkPosition> positions;

    public WorkStructure(Point position, Dimension dimension, List<WorkPosition> positions) {
        super(position, dimension);
        this.positions = positions;
    }

    public List<WorkPosition> getPositions() {
        return this.positions;
    }

    public boolean isFullyStaffed() {
        for (WorkPosition position : this.getPositions()) {
            if (position.getWorker() == null) {
                return false;
            }
        }
        return true;
    }
}
