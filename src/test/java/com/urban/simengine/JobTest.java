package com.urban.simengine;

import com.urban.simengine.agents.HumanAgent;
import junit.framework.TestCase;

import com.urban.simengine.structures.WorkStructure;

import java.awt.*;
import java.util.*;

public class JobTest extends TestCase {
    public void testConstructorUnfilled() {
        Set<Job> positions = new HashSet<Job>();

        WorkStructure workStructure = new WorkStructure(new Point(1, 2), new Dimension(3, 4), positions);
        Job job = new Job(SkillLevel.EXPERT, workStructure);

        assertEquals(SkillLevel.EXPERT, job.getSkillLevel());
        assertEquals(workStructure, job.getWorkStructure());
    }

    public void testConstructorFilled() {
        Set<Job> positions = new HashSet<Job>();

        WorkStructure workStructure = new WorkStructure(new Point(1, 2), new Dimension(3, 4), positions);
        HumanAgent human = new HumanAgent(new GregorianCalendar(2000, 12, 25), SkillLevel.EXPERT);
        Job job = new Job(SkillLevel.EXPERT, workStructure, human);

        assertEquals(SkillLevel.EXPERT, job.getSkillLevel());
        assertEquals(workStructure, job.getWorkStructure());
        assertEquals(human, job.getHuman());
    }

    public void testSetWorker() {
        Set<Job> positions = new HashSet<Job>();

        WorkStructure workStructure = new WorkStructure(new Point(1, 2), new Dimension(3, 4), positions);
        Job job = new Job(SkillLevel.EXPERT, workStructure);

        HumanAgent human = new HumanAgent(new GregorianCalendar(2000, 12, 25), SkillLevel.EXPERT);

        job.setHuman(human);

        assertEquals(human, job.getHuman());

    }
}
