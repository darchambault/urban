package com.urban.simengine.structures;
import com.urban.simengine.agents.HumanAgent;

import java.awt.Point;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

public class ResidenceStructure extends BasicStructure {
    private int maximumResidents;
    private List<HumanAgent> residents;

    public ResidenceStructure(Point position, Dimension dimension, int maximumResidents) {
        super(position, dimension);
        this.maximumResidents = maximumResidents;
        this.residents = new ArrayList<HumanAgent>();
    }

    public ResidenceStructure(Point position, Dimension dimension, int maximumResidents, List<HumanAgent> residents) {
        super(position, dimension);
        this.maximumResidents = maximumResidents;
        this.residents = residents;
    }

    public int getMaximumResidents() {
        return this.maximumResidents;
    }

    public List<HumanAgent> getResidents() {
        return this.residents;
    }
}
