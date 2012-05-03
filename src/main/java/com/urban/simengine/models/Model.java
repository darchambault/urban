package com.urban.simengine.models;

import com.urban.simengine.Job;
import com.urban.simengine.managers.time.TimeManager;
import com.urban.simengine.structures.ResidenceStructure;
import com.urban.simengine.structures.WorkStructure;

import java.util.Set;

public interface Model {
    public TimeManager getTimeManager();

    public Set<ResidenceStructure> getResidences();

    public Set<WorkStructure> getWorkplaces();

    public Set<Job> getUnfilledJobs();

    public void processTick();

    public boolean isComplete();
}
