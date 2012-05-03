package com.urban.simengine;

import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.structures.WorkStructure;

public interface Job {
    public SkillLevel getSkillLevel();

    public WorkStructure getWorkStructure();

    public HumanAgent getHuman();

    public Job setHuman(HumanAgent human);
}
