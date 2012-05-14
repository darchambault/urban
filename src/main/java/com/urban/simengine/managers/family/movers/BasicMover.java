package com.urban.simengine.managers.family.movers;

import com.urban.simengine.Family;
import com.urban.simengine.structures.ResidenceStructure;

import java.util.HashSet;
import java.util.Set;

public class BasicMover implements Mover {
    public Set<Family> moveIn(Set<Family> homelessFamilies, Set<ResidenceStructure> residences) {
        Set<Family> families = new HashSet<Family>();

        for (Family family : homelessFamilies) {
            if (family.getResidence() == null) {
                ResidenceStructure residence = this.findResidenceWithVacancy(residences);
                if (residence == null) {
                    return families;
                }
                family.setResidence(residence);
                residence.getFamilies().add(family);
                families.add(family);
            }
        }

        return families;
    }

    private ResidenceStructure findResidenceWithVacancy(Set<ResidenceStructure> residences) {
        for (ResidenceStructure residence : residences) {
            if (residence.getFamilies().size() < residence.getMaximumFamilies()) {
                return residence;
            }
        }
        return null;
    }
}
