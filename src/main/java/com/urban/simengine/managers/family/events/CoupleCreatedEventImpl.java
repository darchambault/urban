package com.urban.simengine.managers.family.events;

import com.urban.simengine.Family;

public class CoupleCreatedEventImpl implements CoupleCreatedEvent  {
    private Family family;

    public CoupleCreatedEventImpl(Family family) {
        this.family = family;
    }

    public Family getFamily() {
        return this.family;
    }
}
