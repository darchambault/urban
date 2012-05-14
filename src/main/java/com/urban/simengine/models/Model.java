package com.urban.simengine.models;

import com.google.common.eventbus.EventBus;
import com.urban.simengine.Job;
import com.urban.simengine.managers.family.FamilyManager;
import com.urban.simengine.managers.population.PopulationManager;
import com.urban.simengine.managers.time.TimeManager;
import com.urban.simengine.structures.ResidenceStructure;
import com.urban.simengine.structures.WorkStructure;

import java.util.Set;

public interface Model {
    public EventBus getEventBus();

    public TimeManager getTimeManager();

    public PopulationManager getPopulationManager();

    public FamilyManager getFamilyManager();

    public Set<ResidenceStructure> getResidences();

    public Set<ResidenceStructure> getResidencesWithVacancy();

    public Set<WorkStructure> getWorkplaces();

    public Set<Job> getUnfilledJobs();

    public void processTick();

    public boolean isComplete();
}
