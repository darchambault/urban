package com.urban.simengine;

import com.urban.simengine.agents.WorkerAgent;
import com.urban.simengine.structures.WorkStructure;

public class WorkPosition {
    private SkillLevel skillLevel;
    private WorkStructure workStructure;
    private WorkerAgent worker;

    public WorkPosition(SkillLevel skillLevel, WorkStructure workStructure) {
        this.skillLevel = skillLevel;
        this.workStructure = workStructure;
    }

    public WorkPosition(SkillLevel skillLevel, WorkStructure workStructure, WorkerAgent worker) {
        this.skillLevel = skillLevel;
        this.workStructure = workStructure;
        this.worker = worker;
    }

    public SkillLevel getSkillLevel() {
        return this.skillLevel;
    }

    public WorkStructure getWorkStructure() {
        return this.workStructure;
    }

    public WorkerAgent getWorker() {
        return this.worker;
    }

    public WorkPosition setWorker(WorkerAgent worker) {
        this.worker = worker;
        return this;
    }
}
