package com.urban.simengine.models;

import com.urban.simengine.PopulationManager;
import com.urban.simengine.TimeManager;
import com.urban.simengine.structures.ResidenceStructure;
import com.urban.simengine.structures.WorkStructure;

import java.util.GregorianCalendar;
import java.util.Set;

public class TimeLimitModel extends AbstractModel {
    protected GregorianCalendar endDate;

    public TimeLimitModel(TimeManager timeManager, GregorianCalendar endDate, PopulationManager populationManager, Set<ResidenceStructure> residences, Set<WorkStructure> workplaces) {
        super(timeManager, populationManager, residences, workplaces);
        this.endDate = endDate;
    }

    public GregorianCalendar getEndDate() {
        return this.endDate;
    }

    public boolean isComplete() {
        return (this.getTimeManager().getCurrentDate().compareTo(this.endDate) >= 0);
    }
}
