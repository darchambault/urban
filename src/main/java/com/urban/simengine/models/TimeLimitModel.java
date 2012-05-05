package com.urban.simengine.models;

import com.google.common.eventbus.EventBus;
import com.urban.simengine.managers.population.PopulationManager;
import com.urban.simengine.managers.time.TimeManager;
import com.urban.simengine.structures.ResidenceStructure;
import com.urban.simengine.structures.WorkStructure;

import java.util.GregorianCalendar;
import java.util.Set;

public class TimeLimitModel extends ModelAbstract {
    protected GregorianCalendar endDate;

    public TimeLimitModel(EventBus eventBus, TimeManager timeManager, GregorianCalendar endDate, PopulationManager populationManager, Set<ResidenceStructure> residences, Set<WorkStructure> workplaces) {
        super(eventBus, timeManager, populationManager, residences, workplaces);
        this.endDate = endDate;
    }

    public GregorianCalendar getEndDate() {
        return this.endDate;
    }

    public boolean isComplete() {
        return (this.getTimeManager().getCurrentDate().compareTo(this.endDate) >= 0);
    }
}
