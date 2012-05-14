package com.urban.simengine.managers.family;

import com.urban.simengine.Family;
import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.managers.family.childmovers.ChildMover;
import com.urban.simengine.managers.family.couplematchers.CoupleMatcher;
import com.urban.simengine.managers.family.events.ChildMovedOutEventImpl;
import com.urban.simengine.managers.family.events.CoupleCreatedEventImpl;

import com.google.common.eventbus.EventBus;
import com.urban.simengine.managers.family.events.FamilyMovedInEventImpl;
import com.urban.simengine.managers.family.movers.Mover;
import com.urban.simengine.structures.ResidenceStructure;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FamilyManagerImpl implements FamilyManager {
    private EventBus eventBus;
    private Mover mover;
    private CoupleMatcher coupleMatcher;
    private ChildMover childMover;
    private Set<Family> families = new HashSet<Family>();

    public FamilyManagerImpl(EventBus eventBus, Mover mover, CoupleMatcher coupleMatcher, ChildMover childMover) {
        this.eventBus = eventBus;
        this.mover = mover;
        this.coupleMatcher = coupleMatcher;
        this.childMover = childMover;
    }

    public Set<Family> getFamilies() {
        return this.families;
    }

    public Set<Family> getHomelessFamilies() {
        Set<Family> homelessFamilies = new HashSet<Family>();
        for (Family family : this.getFamilies()) {
            if (family.getResidence() == null) {
                homelessFamilies.add(family);
            }
        }
        return Collections.unmodifiableSet(homelessFamilies);
    }

    public void processTick(Calendar currentDate, Set<ResidenceStructure> residencesWithVacancy) {
        Set<Family> newCouples = this.coupleMatcher.createCouples(this.getFamilies());
        for (Family family : newCouples) {
            this.eventBus.post(new CoupleCreatedEventImpl(family));
        }

        Set<HumanAgent> newAdults = this.childMover.moveOutAdultChildren(this.getFamilies(), currentDate);
        for (HumanAgent newAdult : newAdults) {
            this.getFamilies().add(newAdult.getFamily());
            this.eventBus.post(new ChildMovedOutEventImpl(newAdult));
        }

        this.pruneFamilies();

        Set<Family> movedInFamilies = this.mover.moveIn(this.getHomelessFamilies(), residencesWithVacancy);
        for (Family family : movedInFamilies) {
            this.eventBus.post(new FamilyMovedInEventImpl(family));
        }
    }

    private void pruneFamilies() {
        Set<Family> familiesToPrune = new HashSet<Family>();
        for (Family family : this.getFamilies()) {
            if (family.getMembers().size() == 0) {
                familiesToPrune.add(family);
            }
        }
        for (Family family : familiesToPrune) {
            this.getFamilies().remove(family);
        }
    }
}
