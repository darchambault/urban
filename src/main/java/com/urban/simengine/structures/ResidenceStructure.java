package com.urban.simengine.structures;

import com.urban.simengine.Family;

import java.util.Set;

public interface ResidenceStructure extends BasicStructure {
    public int getMaximumFamilies();

    public Set<Family> getFamilies();
}
