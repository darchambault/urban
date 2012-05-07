package com.urban.simengine.managers.population;

import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.managers.population.jobfinders.JobFinder;
import com.urban.simengine.models.Model;

import java.util.Set;

public interface PopulationManager {
    public PopulationManager addHuman(HumanAgent human);

    public Set<HumanAgent> getHumans();

    public Set<HumanAgent> getUnemployedHumans();

    public Set<HumanAgent> getEmployedHumans();

    public void processTick(Model model);
}
