package com.urban.simengine;


import com.urban.simengine.agents.HumanAgent;

import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import static ch.lambdaj.Lambda.*;
import static ch.lambdaj.collection.LambdaCollections.*;
import static org.hamcrest.Matchers.*;

public class PopulationManager {
    Set<HumanAgent> allHumans;

    public PopulationManager() {
        this.allHumans = new HashSet<HumanAgent>();
    }

    public PopulationManager(Set<HumanAgent> humans) {
        this.allHumans = humans;
    }

    public PopulationManager addHuman(HumanAgent human) {
        this.allHumans.add(human);
        return this;
    }

    public Set<HumanAgent> getAllHumans() {
        return this.allHumans;
    }

    public Set<HumanAgent> getUnemployedHumans() {
        return Collections.unmodifiableSet(with(this.allHumans)
                .retain(having(on(HumanAgent.class).getJob(), nullValue())));
    }

    public Set<HumanAgent> getEmployedHumans() {
        return Collections.unmodifiableSet(with(this.allHumans)
                .retain(having(on(HumanAgent.class).getJob(), notNullValue())));
    }

    public void processTick(GregorianCalendar currentDate) {
        Set<HumanAgent> unemployedHumans = this.getUnemployedHumans();

        //TODO: finish implementing processTick for PopulationManager
        for (HumanAgent human : unemployedHumans) {
//            JobFinder.findJob(human, jobs)
        }
    }
}
