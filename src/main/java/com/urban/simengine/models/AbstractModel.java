package com.urban.simengine.models;

import com.urban.simengine.PopulationManager;
import com.urban.simengine.TimeManager;
import com.urban.simengine.structures.ResidenceStructure;
import com.urban.simengine.structures.WorkStructure;

import java.util.Set;

abstract public class AbstractModel {
    private PopulationManager populationManager;
    private TimeManager timeManager;

    private Set<ResidenceStructure> residences;
    private Set<WorkStructure> workplaces;

    public AbstractModel(TimeManager timeManager, PopulationManager populationManager, Set<ResidenceStructure> residences, Set<WorkStructure> workplaces) {
        this.residences = residences;
        this.workplaces = workplaces;

        this.populationManager = populationManager;
        this.timeManager = timeManager;
    }

    public PopulationManager getPopulationManager() {
        return this.populationManager;
    }

    public TimeManager getTimeManager() {
        return this.timeManager;
    }

    public void tick() {

        populationManager.processTick(timeManager.getCurrentDate());

        timeManager.tick();
    }

    public boolean isComplete() {
        return false;
    }
}
