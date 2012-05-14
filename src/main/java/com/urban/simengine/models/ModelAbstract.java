package com.urban.simengine.models;

import com.google.common.eventbus.EventBus;
import com.urban.simengine.Job;
import com.urban.simengine.managers.family.FamilyManager;
import com.urban.simengine.managers.population.PopulationManager;
import com.urban.simengine.managers.time.TimeManager;
import com.urban.simengine.structures.ResidenceStructure;
import com.urban.simengine.structures.WorkStructure;

import java.util.HashSet;
import java.util.Set;

abstract public class ModelAbstract implements Model {
    private EventBus eventBus;

    private TimeManager timeManager;
    private PopulationManager populationManager;
    private FamilyManager familyManager;

    private Set<ResidenceStructure> residences;
    private Set<WorkStructure> workplaces;

    public ModelAbstract(EventBus eventBus, TimeManager timeManager, PopulationManager populationManager,
                         FamilyManager familyManager, Set<ResidenceStructure> residences,
                         Set<WorkStructure> workplaces) {
        this.eventBus = eventBus;

        this.timeManager = timeManager;
        this.populationManager = populationManager;
        this.familyManager = familyManager;

        this.residences = residences;
        this.workplaces = workplaces;
    }

    public EventBus getEventBus() {
        return this.eventBus;
    }

    public TimeManager getTimeManager() {
        return this.timeManager;
    }

    public PopulationManager getPopulationManager() {
        return this.populationManager;
    }

    public FamilyManager getFamilyManager() {
        return this.familyManager;
    }

    public Set<ResidenceStructure> getResidences() {
        return this.residences;
    }

    public Set<ResidenceStructure> getResidencesWithVacancy() {
        HashSet<ResidenceStructure> residencesWithVacancy = new HashSet<ResidenceStructure>();

        for (ResidenceStructure residenceStructure : this.getResidences()) {
            if (residenceStructure.getFamilies().size() < residenceStructure.getMaximumFamilies()) {
                residencesWithVacancy.add(residenceStructure);
            }
        }

        return residencesWithVacancy;
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
        populationManager.processTick(this.getUnfilledJobs());
        familyManager.processTick(this.getTimeManager().getCurrentDate(), this.getResidencesWithVacancy());
    }

    abstract public boolean isComplete();
}
