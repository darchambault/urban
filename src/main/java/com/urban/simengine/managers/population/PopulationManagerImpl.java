package com.urban.simengine.managers.population;

import com.urban.simengine.Job;
import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.managers.population.events.BirthEventImpl;
import com.urban.simengine.managers.population.events.JobFoundEventImpl;
import com.urban.simengine.managers.population.growthmodels.GrowthModel;
import com.urban.simengine.managers.population.jobfinders.JobFinder;

import com.google.common.eventbus.EventBus;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PopulationManagerImpl implements PopulationManager {
    private EventBus eventBus;
    private JobFinder jobFinder;
    private GrowthModel growthModel;

    private int birthRate = 17;

    private Set<HumanAgent> humans = new HashSet<HumanAgent>();

    public PopulationManagerImpl(EventBus eventBus, JobFinder jobFinder, GrowthModel growthModel) {
        this.eventBus = eventBus;
        this.jobFinder = jobFinder;
        this.growthModel = growthModel;
    }

    public Set<HumanAgent> getHumans() {
        return this.humans;
    }

    public Set<HumanAgent> getUnemployedHumans() {
        Set<HumanAgent> unemployedHumans = new HashSet<HumanAgent>();
        for (HumanAgent human : this.getHumans()) {
            if (human.getJob() == null) {
                unemployedHumans.add(human);
            }
        }
        return Collections.unmodifiableSet(unemployedHumans);
    }

    public Set<HumanAgent> getEmployedHumans() {
        Set<HumanAgent> employedHumans = new HashSet<HumanAgent>();
        for (HumanAgent human : this.getHumans()) {
            if (human.getJob() != null) {
                employedHumans.add(human);
            }
        }
        return Collections.unmodifiableSet(employedHumans);
    }

    public Set<HumanAgent> getSingleHumans() {
        Set<HumanAgent> singleHumans = new HashSet<HumanAgent>();
        for (HumanAgent human : this.getHumans()) {
            if (human.getFamily().getMembers().size() == 1) {
                singleHumans.add(human);
            }
        }
        return Collections.unmodifiableSet(singleHumans);
    }

    public int getBirthRate() {
        return this.birthRate;
    }

    public PopulationManager setBirthRate(int birthRate) {
        this.birthRate = birthRate;
        return this;
    }

    public void processTick(Calendar currentDate, int tickLength, int tickLengthUnit, Set<Job> unfilledJobs) {
        Set<HumanAgent> humansWhoFoundJobs = this.jobFinder.findJobs(this.getUnemployedHumans(), unfilledJobs);
        for (HumanAgent human : humansWhoFoundJobs) {
            this.eventBus.post(new JobFoundEventImpl(human));
        }

        Set<HumanAgent> newBirths = this.growthModel.performGrowth(this.getHumans(), this.getBirthRate(), currentDate, tickLength, tickLengthUnit);
        for (HumanAgent human : newBirths) {
            this.getHumans().add(human);
            this.eventBus.post(new BirthEventImpl(human));
        }
    }
}
