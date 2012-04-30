package com.urban.simengine.agents;

import com.urban.simengine.SkillLevel;
import com.urban.simengine.WorkPosition;
import com.urban.simengine.structures.ResidenceStructure;
import java.util.Calendar;

public class WorkerAgent extends HumanAgent {
    private WorkPosition workPosition;

    public WorkerAgent(Calendar dateOfBirth, SkillLevel skillLevel, ResidenceStructure residence) {
        super(dateOfBirth, skillLevel, residence);
    }

    public WorkerAgent(Calendar dateOfBirth, SkillLevel skillLevel, ResidenceStructure residence, WorkPosition workPosition) {
        super(dateOfBirth, skillLevel, residence);
        this.workPosition = workPosition;
    }

    public WorkPosition getWorkPosition() {
        return this.workPosition;
    }
}
