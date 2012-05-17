package com.urban.simengine.managers.population.growthmodels;

import com.urban.simengine.agents.HumanAgent;

import java.util.Calendar;
import java.util.Set;

public interface GrowthModel {
    public Set<HumanAgent> performGrowth(Set<HumanAgent> humans, int yearlyBirthRate, Calendar currentDate, int tickLength, int tickLengthUnit);
}
