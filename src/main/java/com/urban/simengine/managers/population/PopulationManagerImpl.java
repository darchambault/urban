package com.urban.simengine.managers.population;

import com.google.common.eventbus.EventBus;
import com.urban.simengine.Job;
import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.managers.population.events.JobFoundEventImpl;
import com.urban.simengine.managers.population.jobfinders.JobFinder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PopulationManagerImpl implements PopulationManager {
    private JobFinder jobFinder;
    private EventBus eventBus;
    private Set<HumanAgent> humans = new HashSet<HumanAgent>();

    public PopulationManagerImpl(JobFinder jobFinder, EventBus eventBus) {
        this.jobFinder = jobFinder;
        this.eventBus = eventBus;
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

    public void processTick(Set<Job> unfilledJobs) {
        Set<HumanAgent> humansWhoFoundJobs = this.jobFinder.findJobs(this.getUnemployedHumans(), unfilledJobs);
        for (HumanAgent human : humansWhoFoundJobs) {
            this.eventBus.post(new JobFoundEventImpl(human));
        }
    }
}
