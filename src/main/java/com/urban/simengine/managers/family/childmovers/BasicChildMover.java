package com.urban.simengine.managers.family.childmovers;

import com.urban.simengine.Family;
import com.urban.simengine.FamilyImpl;
import com.urban.simengine.agents.HumanAgent;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class BasicChildMover implements ChildMover {
    public Set<HumanAgent> moveOutAdultChildren(Set<Family> families, Calendar currentDate) {
        Set<HumanAgent> childrenToMoveOut = new HashSet<HumanAgent>();
        for (Family family : families) {
            if (family.getMembers().size() > 1) {
                for (HumanAgent human : family.getMembers()) {
                    if (human.getAge(currentDate) >= 18 && human.getParents().size() > 0 && family.getMembers().containsAll(human.getParents())) {
                        childrenToMoveOut.add(human);
                    }
                }
            }
        }
        Set<HumanAgent> newAdults = new HashSet<HumanAgent>();
        for (HumanAgent child : childrenToMoveOut) {
            this.moveOutChild(child);
            newAdults.add(child);
        }
        return newAdults;
    }

    private void moveOutChild(HumanAgent child) {
        Family newFamily = this.getNewFamilyInstance();
        Family oldFamily = child.getFamily();

        oldFamily.getMembers().remove(child);
        child.setFamily(newFamily);
        newFamily.getMembers().add(child);
    }

    private Family getNewFamilyInstance() {
        return new FamilyImpl();
    }
}
