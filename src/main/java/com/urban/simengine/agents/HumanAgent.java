package com.urban.simengine.agents;

import com.urban.simengine.Job;
import com.urban.simengine.SkillLevel;
import com.urban.simengine.structures.ResidenceStructure;

import java.util.Calendar;

public interface HumanAgent extends BasicAgent {
    public Calendar getDateOfBirth();

    public SkillLevel getSkillLevel();

    public ResidenceStructure getResidence();

    public HumanAgent setResidence(ResidenceStructure residence);

    public Job getJob();

    public HumanAgent setJob(Job job);
}
