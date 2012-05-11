package com.urban.simengine.models;

import com.google.common.eventbus.EventBus;
import com.urban.simengine.managers.family.FamilyManager;
import com.urban.simengine.managers.population.PopulationManager;
import com.urban.simengine.managers.time.TimeManager;
import com.urban.simengine.structures.ResidenceStructure;
import com.urban.simengine.structures.WorkStructure;

import java.util.Calendar;
import java.util.Set;

public class TimeLimitModel extends ModelAbstract {
    protected Calendar endDate;

    public TimeLimitModel(EventBus eventBus, TimeManager timeManager, Calendar endDate,
                          PopulationManager populationManager, FamilyManager familyManager,
                          Set<ResidenceStructure> residences, Set<WorkStructure> workplaces) {
        super(eventBus, timeManager, populationManager, familyManager, residences, workplaces);
        this.endDate = endDate;
    }

    public Calendar getEndDate() {
        return this.endDate;
    }

    public boolean isComplete() {
        return (this.getTimeManager().getCurrentDate().compareTo(this.endDate) >= 0);
    }
}
