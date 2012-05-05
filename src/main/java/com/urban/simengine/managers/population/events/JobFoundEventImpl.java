package com.urban.simengine.managers.population.events;

import com.urban.simengine.agents.HumanAgent;

public class JobFoundEventImpl implements JobFoundEvent  {
    private HumanAgent human;

    public JobFoundEventImpl(HumanAgent human) {
        this.human = human;
    }

    public HumanAgent getHuman() {
        return this.human;
    }
}
