package com.urban.simengine;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.structures.ResidenceStructure;

import java.util.Collections;
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
        return Collections.unmodifiableSet(Sets.filter(this.members, new Predicate<HumanAgent>() {
            public boolean apply(HumanAgent human) {
                return (human.getParents().isEmpty() || !human.getFamily().getMembers().containsAll(human.getParents()));
            }
        }));
    }

    public Set<HumanAgent> getChildren() {
        return Collections.unmodifiableSet(Sets.filter(this.members, new Predicate<HumanAgent>() {
            public boolean apply(HumanAgent human) {
                return (human.getChildren().isEmpty() && human.getFamily().getMembers().containsAll(human.getParents()));
            }
        }));
    }

    public ResidenceStructure getResidence() {
        return this.residence;
    }

    public Family setResidence(ResidenceStructure residence) {
        this.residence = residence;
        return this;
    }
}
