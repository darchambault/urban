package com.urban.simengine.structures;

import com.urban.simengine.agents.HumanAgent;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class ResidenceStructureImpl extends BasicStructureImpl implements ResidenceStructure {
    private int maximumResidents;
    private Set<HumanAgent> residents;

    public ResidenceStructureImpl(Point position, Dimension dimension, int maximumResidents) {
        super(position, dimension);
        this.maximumResidents = maximumResidents;
        this.residents = new HashSet<HumanAgent>();
    }

    public ResidenceStructureImpl(Point position, Dimension dimension, int maximumResidents, Set<HumanAgent> residents) {
        super(position, dimension);
        this.maximumResidents = maximumResidents;
        this.residents = residents;
    }

    public int getMaximumResidents() {
        return this.maximumResidents;
    }

    public Set<HumanAgent> getResidents() {
        return this.residents;
    }
}
