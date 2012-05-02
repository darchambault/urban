package com.urban.simengine;

import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.structures.WorkStructure;

public class Job {
    private SkillLevel skillLevel;
    private WorkStructure workStructure;
    private HumanAgent human;

    public Job(SkillLevel skillLevel, WorkStructure workStructure) {
        this.skillLevel = skillLevel;
        this.workStructure = workStructure;
    }

    public Job(SkillLevel skillLevel, WorkStructure workStructure, HumanAgent human) {
        this.skillLevel = skillLevel;
        this.workStructure = workStructure;
        this.human = human;
    }

    public SkillLevel getSkillLevel() {
        return this.skillLevel;
    }

    public WorkStructure getWorkStructure() {
        return this.workStructure;
    }

    public HumanAgent getHuman() {
        return this.human;
    }

    public Job setHuman(HumanAgent human) {
        this.human = human;
        return this;
    }
}
