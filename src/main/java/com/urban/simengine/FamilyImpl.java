package com.urban.simengine;

import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.structures.ResidenceStructure;

import java.util.HashSet;
import java.util.Set;

public class FamilyImpl implements Family {
    private Set<HumanAgent> members = new HashSet<HumanAgent>();
    private ResidenceStructure residence;

    public FamilyImpl() {
    }

    public FamilyImpl(Set<HumanAgent> members, ResidenceStructure residence) {
        this.members = members;
        this.residence = residence;
    }

    public Set<HumanAgent> getMembers() {
        return this.members;
    }

    public Set<HumanAgent> getParents() {
        Set<HumanAgent> parents = new HashSet<HumanAgent>();
        for (HumanAgent human : this.getMembers()) {
            if (human.getParents().isEmpty() || !this.getMembers().containsAll(human.getParents())) {
                parents.add(human);
            }
        }
        return parents;
    }

    public Set<HumanAgent> getChildren() {
        Set<HumanAgent> children = new HashSet<HumanAgent>();
        for (HumanAgent human : this.getMembers()) {
            if (human.getChildren().isEmpty() && this.getMembers().containsAll(human.getParents())) {
                children.add(human);
            }
        }
        return children;
    }

    public ResidenceStructure getResidence() {
        return this.residence;
    }

    public Family setResidence(ResidenceStructure residence) {
        this.residence = residence;
        return this;
    }
}
