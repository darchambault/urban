package com.urban.simengine.managers.population;

import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.managers.population.jobfinders.JobFinder;
import com.urban.simengine.models.Model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PopulationManagerImpl implements PopulationManager {
    private Set<HumanAgent> humans;
    private JobFinder jobFinder;

    public PopulationManagerImpl(JobFinder jobFinder) {
        this.humans = new HashSet<HumanAgent>();
        this.jobFinder = jobFinder;
    }

    public PopulationManagerImpl(JobFinder jobFinder, Set<HumanAgent> humans) {
        this.humans = humans;
        this.jobFinder = jobFinder;
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
        this.getJobFinder().findJobs(this.getUnemployedHumans(), model.getUnfilledJobs());
    }
}
