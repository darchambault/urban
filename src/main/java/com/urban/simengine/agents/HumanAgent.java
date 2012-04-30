package com.urban.simengine.agents;

import com.urban.simengine.SkillLevel;
import com.urban.simengine.structures.ResidenceStructure;

import java.util.Calendar;

public class HumanAgent extends BasicAgent {
    private Calendar dateOfBirth;
    private ResidenceStructure residence;
    private SkillLevel skillLevel;

    public HumanAgent(Calendar dateOfBirth, SkillLevel skillLevel) {
        this.dateOfBirth = dateOfBirth;
        this.skillLevel = skillLevel;
    }

    public HumanAgent(Calendar dateOfBirth, SkillLevel skillLevel, ResidenceStructure residence) {
        this.dateOfBirth = dateOfBirth;
        this.skillLevel = skillLevel;
        this.residence = residence;
    }

    public ResidenceStructure getResidence() {
        return this.residence;
    }

    public Calendar getDateOfBirth() {
        return this.dateOfBirth;
    }

    public SkillLevel getSkillLevel() {
        return this.skillLevel;
    }

    public void setResidence(ResidenceStructure residence) {
        this.residence = residence;
    }
}
