package com.urban.simengine;
import com.urban.simengine.agents.WorkerAgent;
import com.urban.simengine.structures.ResidenceStructure;
import com.urban.simengine.structures.WorkStructure;
import junit.framework.TestCase;

import java.awt.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class WorkPositionTest extends TestCase {
    public void testConstructorUnfilled() {
        ArrayList<WorkPosition> positions = new ArrayList<WorkPosition>();

        WorkStructure workStructure = new WorkStructure(new Point(1, 2), new Dimension(3, 4), positions);
        WorkPosition workPosition = new WorkPosition(SkillLevel.EXPERT, workStructure);

        assertEquals(SkillLevel.EXPERT, workPosition.getSkillLevel());
        assertEquals(workStructure, workPosition.getWorkStructure());
    }

    public void testConstructorFilled() {
        ArrayList<WorkPosition> positions = new ArrayList<WorkPosition>();

        WorkStructure workStructure = new WorkStructure(new Point(1, 2), new Dimension(3, 4), positions);
        ResidenceStructure residence = new ResidenceStructure(new Point(1, 2), new Dimension(3, 4), 2);
        WorkerAgent worker = new WorkerAgent(new GregorianCalendar(2000, 12, 25), SkillLevel.EXPERT, residence);
        WorkPosition workPosition = new WorkPosition(SkillLevel.EXPERT, workStructure, worker);

        assertEquals(SkillLevel.EXPERT, workPosition.getSkillLevel());
        assertEquals(workStructure, workPosition.getWorkStructure());
        assertEquals(worker, workPosition.getWorker());
    }

    public void testSetWorker() {
        ArrayList<WorkPosition> positions = new ArrayList<WorkPosition>();

        WorkStructure workStructure = new WorkStructure(new Point(1, 2), new Dimension(3, 4), positions);
        WorkPosition workPosition = new WorkPosition(SkillLevel.EXPERT, workStructure);

        ResidenceStructure residence = new ResidenceStructure(new Point(1, 2), new Dimension(3, 4), 2);
        WorkerAgent worker = new WorkerAgent(new GregorianCalendar(2000, 12, 25), SkillLevel.EXPERT, residence);

        workPosition.setWorker(worker);

        assertEquals(worker, workPosition.getWorker());

    }
}
