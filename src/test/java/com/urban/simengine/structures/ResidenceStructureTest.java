package com.urban.simengine.structures;

import junit.framework.TestCase;

import com.urban.simengine.SkillLevel;
import com.urban.simengine.agents.HumanAgent;

import java.awt.*;
import java.util.*;

public class ResidenceStructureTest extends TestCase {
    public void testConstructorWithoutResidents() {
        ResidenceStructure residence = new ResidenceStructure(new Point(1, 2), new Dimension(3, 4), 2);

        assertEquals(2, residence.getMaximumResidents());
        assertEquals(0, residence.getResidents().size());
    }

    public void testConstructorWithResidents() {
        HumanAgent human1 = new HumanAgent(new GregorianCalendar(2000, 12, 25), SkillLevel.BASIC);
        HumanAgent human2 = new HumanAgent(new GregorianCalendar(2001, 12, 26), SkillLevel.EXPERT);
        Set<HumanAgent> residents = new HashSet<HumanAgent>();
        residents.add(human1);
        residents.add(human2);

        ResidenceStructure residence = new ResidenceStructure(new Point(1, 2), new Dimension(3, 4), 2, residents);

        assertEquals(2, residence.getMaximumResidents());
        assertEquals(2, residence.getResidents().size());
        assertTrue(residence.getResidents().contains(human1));
        assertTrue(residence.getResidents().contains(human2));
    }
}
