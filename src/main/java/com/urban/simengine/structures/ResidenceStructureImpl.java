package com.urban.simengine.structures;

import com.urban.simengine.Family;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class ResidenceStructureImpl extends BasicStructureImpl implements ResidenceStructure {
    private int maximumFamilies;
    private Set<Family> families;

    public ResidenceStructureImpl(Point position, Dimension dimension, int maximumFamilies) {
        super(position, dimension);
        this.maximumFamilies = maximumFamilies;
        this.families = new HashSet<Family>();
    }

    public ResidenceStructureImpl(Point position, Dimension dimension, int maximumFamilies, Set<Family> families) {
        super(position, dimension);
        this.maximumFamilies = maximumFamilies;
        this.families = families;
    }

    public int getMaximumFamilies() {
        return this.maximumFamilies;
    }

    public Set<Family> getFamilies() {
        return this.families;
    }
}
