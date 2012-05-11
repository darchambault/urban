package com.urban.simengine.managers.family.events;

import com.urban.simengine.agents.HumanAgent;

public class ChildMovedOutEventImpl implements ChildMovedOutEvent  {
    private HumanAgent human;

    public ChildMovedOutEventImpl(HumanAgent human) {
        this.human = human;
    }

    public HumanAgent getHuman() {
        return this.human;
    }
}
