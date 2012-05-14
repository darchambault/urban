package com.urban.simengine.managers.family.events;

import com.urban.simengine.Family;

public class FamilyMovedInEventImpl implements FamilyMovedInEvent {
    private Family family;

    public FamilyMovedInEventImpl(Family family) {
        this.family = family;
    }

    public Family getFamily() {
        return this.family;
    }
}
