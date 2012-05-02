package com.urban.simengine.structures;

import com.urban.simengine.agents.HumanAgent;
import junit.framework.TestCase;

import com.urban.simengine.SkillLevel;
import com.urban.simengine.Job;

import java.awt.*;
import java.util.*;

public class WorkStructureTest extends TestCase {
    public void testConstructorWithoutWorkers() {
        Set<Job> positions = new HashSet<Job>();
        WorkStructure work = new WorkStructure(new Point(1, 2), new Dimension(3, 4), positions);
        Job position1 = new Job(SkillLevel.BASIC, work);
        Job position2 = new Job(SkillLevel.EXPERT, work);
        positions.add(position1);
        positions.add(position2);


        assertEquals(2, work.getJobs().size());
        for (Job job : work.getJobs()) {
            assertNull(job.getHuman());
        }
    }

    public void testConstructorWithWorkers() {
        Set<Job> positions = new HashSet<Job>();
        WorkStructure work = new WorkStructure(new Point(1, 2), new Dimension(3, 4), positions);
        Job position1 = new Job(SkillLevel.BASIC, work);
        Job position2 = new Job(SkillLevel.EXPERT, work);
        positions.add(position1);
        positions.add(position2);


        assertEquals(2, work.getJobs().size());
        for (Job job : work.getJobs()) {
            assertNull(job.getHuman());
        }
    }

    public void testDetectsFullyStaffed() {
        Set<Job> positions = new HashSet<Job>();
        WorkStructure work = new WorkStructure(new Point(1, 2), new Dimension(3, 4), positions);
        Job position1 = new Job(SkillLevel.BASIC, work);
        Job position2 = new Job(SkillLevel.EXPERT, work);
        positions.add(position1);
        positions.add(position2);

        assertFalse(work.isFullyStaffed());

        HumanAgent human1 = new HumanAgent(new GregorianCalendar(2000, 12, 25), SkillLevel.BASIC);
        HumanAgent human2 = new HumanAgent(new GregorianCalendar(2001, 11, 20), SkillLevel.EXPERT);
        position1.setHuman(human1);
        position2.setHuman(human2);

        assertTrue(work.isFullyStaffed());
    }
}
