package com.urban.simengine.agents;

import com.urban.simengine.Job;
import com.urban.simengine.SkillLevel;
import com.urban.simengine.structures.ResidenceStructure;

import java.util.Calendar;

public class HumanAgent extends BasicAgent {
    private Calendar dateOfBirth;
    private SkillLevel skillLevel;
    private ResidenceStructure residence;
    private Job job;

    public HumanAgent(Calendar dateOfBirth, SkillLevel skillLevel) {
        this.dateOfBirth = dateOfBirth;
        this.skillLevel = skillLevel;
    }

    public Calendar getDateOfBirth() {
        return this.dateOfBirth;
    }

    public SkillLevel getSkillLevel() {
        return this.skillLevel;
    }

    public ResidenceStructure getResidence() {
        return this.residence;
    }

    public HumanAgent setResidence(ResidenceStructure residence) {
        this.residence = residence;
        return this;
    }

    public Job getJob() {
        return this.job;
    }

    public HumanAgent setJob(Job job) {
        this.job = job;
        return this;
    }
}
