package com.urban.simengine.agents;

import com.urban.simengine.SkillLevel;
import com.urban.simengine.WorkPosition;
import com.urban.simengine.structures.ResidenceStructure;
import com.urban.simengine.structures.WorkStructure;
import junit.framework.TestCase;

import java.awt.Point;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class WorkerAgentTest extends TestCase {
    public void testConstructorWithoutPosition() {
        Calendar dateOfBirth = new GregorianCalendar(2000, 12, 25);
        ResidenceStructure residence = new ResidenceStructure(new Point(1, 2), new Dimension(3, 4), 2, new ArrayList<HumanAgent>());

        WorkerAgent worker = new WorkerAgent(dateOfBirth, SkillLevel.ADVANCED, residence);

        assertEquals(dateOfBirth, worker.getDateOfBirth());
        assertEquals(residence, worker.getResidence());
        assertEquals(SkillLevel.ADVANCED, worker.getSkillLevel());
    }

    public void testConstructorWithPosition() {
        Calendar dateOfBirth = new GregorianCalendar(2000, 12, 25);
        ResidenceStructure residence = new ResidenceStructure(new Point(1, 2), new Dimension(3, 4), 2, new ArrayList<HumanAgent>());
        ArrayList<WorkPosition> positions = new ArrayList<WorkPosition>();
        WorkStructure workplace = new WorkStructure(new Point(1, 2), new Dimension(3, 4), positions);
        WorkPosition workPosition = new WorkPosition(SkillLevel.ADVANCED, workplace);
        positions.add(workPosition);

        WorkerAgent worker = new WorkerAgent(dateOfBirth, SkillLevel.ADVANCED, residence, workPosition);

        assertEquals(dateOfBirth, worker.getDateOfBirth());
        assertEquals(residence, worker.getResidence());
        assertEquals(SkillLevel.ADVANCED, worker.getSkillLevel());
        assertEquals(workPosition, worker.getWorkPosition());
    }
}
