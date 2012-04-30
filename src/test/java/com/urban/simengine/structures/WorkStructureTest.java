package com.urban.simengine.structures;

import com.urban.simengine.SkillLevel;
import com.urban.simengine.WorkPosition;
import com.urban.simengine.agents.WorkerAgent;
import junit.framework.TestCase;

import java.awt.Point;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class WorkStructureTest extends TestCase {
    public void testConstructorWithoutWorkers() {
        ArrayList<WorkPosition> positions = new ArrayList<WorkPosition>();
        WorkStructure work = new WorkStructure(new Point(1, 2), new Dimension(3, 4), positions);
        WorkPosition position1 = new WorkPosition(SkillLevel.BASIC, work);
        WorkPosition position2 = new WorkPosition(SkillLevel.EXPERT, work);
        positions.add(position1);
        positions.add(position2);


        assertEquals(2, work.getPositions().size());
        assertNull(work.getPositions().get(0).getWorker());
        assertNull(work.getPositions().get(1).getWorker());
    }

    public void testConstructorWithWorkers() {
        ArrayList<WorkPosition> positions = new ArrayList<WorkPosition>();
        WorkStructure work = new WorkStructure(new Point(1, 2), new Dimension(3, 4), positions);
        WorkPosition position1 = new WorkPosition(SkillLevel.BASIC, work);
        WorkPosition position2 = new WorkPosition(SkillLevel.EXPERT, work);
        positions.add(position1);
        positions.add(position2);


        assertEquals(2, work.getPositions().size());
        assertNull(work.getPositions().get(0).getWorker());
        assertNull(work.getPositions().get(1).getWorker());
    }

    public void testDetectsFullyStaffed() {
        ArrayList<WorkPosition> positions = new ArrayList<WorkPosition>();
        WorkStructure work = new WorkStructure(new Point(1, 2), new Dimension(3, 4), positions);
        WorkPosition position1 = new WorkPosition(SkillLevel.BASIC, work);
        WorkPosition position2 = new WorkPosition(SkillLevel.EXPERT, work);
        positions.add(position1);
        positions.add(position2);

        assertFalse(work.isFullyStaffed());

        ResidenceStructure residence = new ResidenceStructure(new Point(1, 2), new Dimension(3, 4), 2);
        WorkerAgent worker1 = new WorkerAgent(new GregorianCalendar(2000, 12, 25), SkillLevel.BASIC, residence);
        WorkerAgent worker2 = new WorkerAgent(new GregorianCalendar(2001, 11, 20), SkillLevel.EXPERT, residence);
        work.getPositions().get(0).setWorker(worker1);
        work.getPositions().get(1).setWorker(worker2);

        assertTrue(work.isFullyStaffed());
    }
}
