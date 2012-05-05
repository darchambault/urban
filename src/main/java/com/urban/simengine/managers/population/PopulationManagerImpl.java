package com.urban.simengine.managers.population;

import com.google.common.eventbus.EventBus;
import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.managers.population.events.JobFoundEventImpl;
import com.urban.simengine.managers.population.jobfinders.JobFinder;
import com.urban.simengine.models.Model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PopulationManagerImpl implements PopulationManager {
    private JobFinder jobFinder;
    private EventBus eventBus;
    private Set<HumanAgent> humans;

    public PopulationManagerImpl(JobFinder jobFinder, EventBus eventBus) {
        this.jobFinder = jobFinder;
        this.eventBus = eventBus;
        this.humans = new HashSet<HumanAgent>();
    }

    public PopulationManagerImpl(JobFinder jobFinder, EventBus eventBus, Set<HumanAgent> humans) {
        this.jobFinder = jobFinder;
        this.eventBus = eventBus;
        this.humans = humans;
    }

    public JobFinder getJobFinder() {
        return this.jobFinder;
    }

    public Set<HumanAgent> getHumans() {
        return this.humans;
    }

    public PopulationManager addHuman(HumanAgent human) {
        this.humans.add(human);
        return this;
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


    public void processTick(Model model) {
        Set<HumanAgent> humansWhoFoundJobs = this.getJobFinder().findJobs(this.getUnemployedHumans(), model.getUnfilledJobs());
        for (HumanAgent human : humansWhoFoundJobs) {
            this.eventBus.post(new JobFoundEventImpl(human));
        }
    }
}
