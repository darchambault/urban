package com.urban.simengine.managers.family.movers;

import com.urban.simengine.Family;
import com.urban.simengine.structures.ResidenceStructure;

import java.util.Set;

public interface Mover {
    public Set<Family> moveIn(Set<Family> homelessFamilies, Set<ResidenceStructure> residences);
}
