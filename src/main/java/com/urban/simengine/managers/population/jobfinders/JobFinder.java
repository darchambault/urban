package com.urban.simengine.managers.population.jobfinders;

import com.urban.simengine.Job;
import com.urban.simengine.agents.HumanAgent;

import java.util.Set;

public interface JobFinder {
    public Set<HumanAgent> findJobs(Set<HumanAgent> unemployedHumans, Set<Job> unfilledJobs);
}
