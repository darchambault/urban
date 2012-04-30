package com.urban.simengine.agents;
import com.urban.simengine.SkillLevel;
import com.urban.simengine.structures.ResidenceStructure;
import junit.framework.TestCase;

import java.awt.Point;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class HumanAgentTest extends TestCase {
    public void testConstructorWithoutResidence() {
        Calendar dateOfBirth = new GregorianCalendar(2000, 11, 25);

        HumanAgent human = new HumanAgent(dateOfBirth, SkillLevel.ADVANCED);

        assertEquals(dateOfBirth, human.getDateOfBirth());
        assertEquals(SkillLevel.ADVANCED, human.getSkillLevel());
        assertTrue(human.getSkillLevel().compareTo(SkillLevel.BASIC) > 0);
    }

    public void testConstructorWithResidence() {
        ResidenceStructure residence = new ResidenceStructure(new Point(1, 2), new Dimension(3, 4), 2, new ArrayList<HumanAgent>());
        Calendar dateOfBirth = new GregorianCalendar(2000, 11, 25);

        HumanAgent human = new HumanAgent(dateOfBirth, SkillLevel.ADVANCED, residence);

        assertEquals(residence, human.getResidence());
        assertEquals(dateOfBirth, human.getDateOfBirth());
        assertEquals(SkillLevel.ADVANCED, human.getSkillLevel());
        assertTrue(human.getSkillLevel().compareTo(SkillLevel.BASIC) > 0);
    }

    public void testSetResidence() {
        ResidenceStructure residence = new ResidenceStructure(new Point(1, 2), new Dimension(3, 4), 2, new ArrayList<HumanAgent>());
        Calendar dateOfBirth = new GregorianCalendar(2000, 11, 25);

        HumanAgent human = new HumanAgent(dateOfBirth, SkillLevel.ADVANCED);

        human.setResidence(residence);

        assertEquals(residence, human.getResidence());
    }
}
