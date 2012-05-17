package com.urban.simengine.managers.population;

import com.urban.simengine.Job;
import com.urban.simengine.agents.HumanAgent;

import java.util.Calendar;
import java.util.Set;

public interface PopulationManager {
    public Set<HumanAgent> getHumans();

    public Set<HumanAgent> getUnemployedHumans();

    public Set<HumanAgent> getEmployedHumans();

    public Set<HumanAgent> getSingleHumans();

    public int getBirthRate();

    public PopulationManager setBirthRate(int birthRate);

    public void processTick(Calendar currentDate, int tickLength, int tickLengthUnit, Set<Job> unfilledJobs);
}
