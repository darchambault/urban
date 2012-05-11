package com.urban.simengine.managers.population;

import com.urban.simengine.Job;
import com.urban.simengine.agents.HumanAgent;

import java.util.Set;

public interface PopulationManager {
    public Set<HumanAgent> getHumans();

    public Set<HumanAgent> getUnemployedHumans();

    public Set<HumanAgent> getEmployedHumans();

    public Set<HumanAgent> getSingleHumans();

    public void processTick(Set<Job> unfilledJobs);
}
