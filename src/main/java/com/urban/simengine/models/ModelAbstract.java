package com.urban.simengine.models;

import com.urban.simengine.Job;
import com.urban.simengine.managers.population.PopulationManager;
import com.urban.simengine.managers.time.TimeManager;
import com.urban.simengine.structures.ResidenceStructure;
import com.urban.simengine.structures.WorkStructure;

import java.util.HashSet;
import java.util.Set;

abstract public class ModelAbstract implements Model {
    private PopulationManager populationManager;
    private TimeManager timeManager;

    private Set<ResidenceStructure> residences;
    private Set<WorkStructure> workplaces;

    public ModelAbstract(TimeManager timeManager, PopulationManager populationManager, Set<ResidenceStructure> residences, Set<WorkStructure> workplaces) {
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

    public Set<ResidenceStructure> getResidences() {
        return this.residences;
    }

    public Set<WorkStructure> getWorkplaces() {
        return this.workplaces;
    }

    public Set<Job> getUnfilledJobs() {
        HashSet<Job> unfilledJobs = new HashSet<Job>();

        for (WorkStructure workplace : this.getWorkplaces()) {
            for (Job job : workplace.getJobs()) {
                if (job.getHuman() == null) {
                    unfilledJobs.add(job);
                }
            }
        }

        return unfilledJobs;
    }

    public void processTick() {
        populationManager.processTick(this);
    }

    abstract public boolean isComplete();
}
