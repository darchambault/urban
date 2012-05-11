package com.urban.simengine.managers.family;

import com.urban.simengine.Family;
import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.managers.family.childmovers.ChildMover;
import com.urban.simengine.managers.family.couplematchers.CoupleMatcher;
import com.urban.simengine.managers.family.events.ChildMovedOutEventImpl;
import com.urban.simengine.managers.family.events.CoupleCreatedEventImpl;

import com.google.common.eventbus.EventBus;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class FamilyManagerImpl implements FamilyManager {
    private EventBus eventBus;
    private CoupleMatcher coupleMatcher;
    private ChildMover childMover;
    private Set<Family> families = new HashSet<Family>();

    public FamilyManagerImpl(EventBus eventBus, CoupleMatcher coupleMatcher, ChildMover childMover) {
        this.eventBus = eventBus;
        this.coupleMatcher = coupleMatcher;
        this.childMover = childMover;
    }

    public Set<Family> getFamilies() {
        return this.families;
    }

    public void processTick(Calendar currentDate) {
        Set<Family> newCouples = this.coupleMatcher.createCouples(this.getFamilies());
        for (Family family : newCouples) {
            this.getFamilies().add(family);
            this.eventBus.post(new CoupleCreatedEventImpl(family));
        }

        Set<HumanAgent> newAdults = this.childMover.moveOutAdultChildren(this.getFamilies(), currentDate);
        for (HumanAgent newAdult : newAdults) {
            this.getFamilies().add(newAdult.getFamily());
            this.eventBus.post(new ChildMovedOutEventImpl(newAdult));
        }
    }
}
