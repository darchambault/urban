package com.urban.simengine.structures;

import com.urban.simengine.SkillLevel;
import junit.framework.TestCase;
import com.urban.simengine.agents.HumanAgent;
import java.awt.Point;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class ResidenceStructureTest extends TestCase {
    public void testConstructorWithoutResidents() {
        ResidenceStructure residence = new ResidenceStructure(new Point(1, 2), new Dimension(3, 4), 2);

        assertEquals(2, residence.getMaximumResidents());
        assertEquals(0, residence.getResidents().size());
    }

    public void testConstructorWithResidents() {
        HumanAgent human1 = new HumanAgent(new GregorianCalendar(2000, 12, 25), SkillLevel.BASIC);
        HumanAgent human2 = new HumanAgent(new GregorianCalendar(2001, 12, 26), SkillLevel.EXPERT);
        ArrayList<HumanAgent> residents = new ArrayList<HumanAgent>();
        residents.add(human1);
        residents.add(human2);

        ResidenceStructure residence = new ResidenceStructure(new Point(1, 2), new Dimension(3, 4), 2, residents);

        assertEquals(2, residence.getMaximumResidents());
        assertEquals(2, residence.getResidents().size());
        assertTrue(residence.getResidents().indexOf(human1) != -1);
        assertTrue(residence.getResidents().indexOf(human2) != -1);
    }
}
