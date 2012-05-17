package com.urban.simengine;

import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.structures.ResidenceStructure;

import java.util.Set;

public interface Family {
    public Set<HumanAgent> getMembers();

    public Set<HumanAgent> getParents();

    public Set<HumanAgent> getChildren();

    public ResidenceStructure getResidence();

    public Family setResidence(ResidenceStructure residence);
}
