package com.urban.simengine.structures;

import com.urban.simengine.agents.HumanAgent;

import java.util.Set;

public interface ResidenceStructure extends BasicStructure {
    public int getMaximumResidents();

    public Set<HumanAgent> getResidents();
}
