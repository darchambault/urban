package com.urban.simengine.managers.population.events;

import com.urban.simengine.agents.HumanAgent;

public class BirthEventImpl implements BirthEvent  {
    private HumanAgent human;

    public BirthEventImpl(HumanAgent human) {
        this.human = human;
    }

    public HumanAgent getHuman() {
        return this.human;
    }
}
