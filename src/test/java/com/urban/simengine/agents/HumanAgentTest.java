package com.urban.simengine.agents;

import com.urban.simengine.Job;
import com.urban.simengine.structures.WorkStructure;
import junit.framework.TestCase;

import com.urban.simengine.SkillLevel;
import com.urban.simengine.structures.ResidenceStructure;

import java.awt.*;
import java.util.*;

public class HumanAgentTest extends TestCase {
    public void testConstructorWithoutResidence() {
        Calendar dateOfBirth = new GregorianCalendar(2000, 11, 25);

        HumanAgent human = new HumanAgent(dateOfBirth, SkillLevel.ADVANCED);

        assertEquals(dateOfBirth, human.getDateOfBirth());
        assertEquals(SkillLevel.ADVANCED, human.getSkillLevel());
        assertTrue(human.getSkillLevel().compareTo(SkillLevel.BASIC) > 0);
    }

    public void testSetResidence() {
        ResidenceStructure residence = new ResidenceStructure(new Point(1, 2), new Dimension(3, 4), 2, new HashSet<HumanAgent>());
        Calendar dateOfBirth = new GregorianCalendar(2000, 11, 25);

        HumanAgent human = new HumanAgent(dateOfBirth, SkillLevel.ADVANCED);

        human.setResidence(residence);

        assertEquals(residence, human.getResidence());
    }

    public void testSetJob() {
        HumanAgent worker = this.withUnemployedWorker();
        Job job = this.withUnfilledJob();

        worker.setJob(job);

        assertEquals(job, worker.getJob());
    }

    private HumanAgent withUnemployedWorker() {
        Calendar dateOfBirth = new GregorianCalendar(2000, 12, 25);
        HumanAgent worker = new HumanAgent(dateOfBirth, SkillLevel.ADVANCED);
        worker.setResidence(new ResidenceStructure(new Point(1, 2), new Dimension(3, 4), 2, new HashSet<HumanAgent>()));
        return worker;
    }

    private Job withUnfilledJob() {
        Set<Job> positions = new HashSet<Job>();
        WorkStructure workplace = new WorkStructure(new Point(1, 2), new Dimension(3, 4), positions);
        Job job = new Job(SkillLevel.ADVANCED, workplace);
        positions.add(job);
        return job;
    }
}
