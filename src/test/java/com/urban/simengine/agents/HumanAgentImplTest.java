package com.urban.simengine.agents;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import com.urban.simengine.Job;
import com.urban.simengine.structures.ResidenceStructure;
import com.urban.simengine.SkillLevel;

import java.util.*;

public class HumanAgentImplTest {
    @Test public void testConstructorWithoutResidence() {
        Calendar dateOfBirthMock = createMock(GregorianCalendar.class);

        replay(dateOfBirthMock);

        HumanAgent human = new HumanAgentImpl(dateOfBirthMock, SkillLevel.ADVANCED);

        assertSame(dateOfBirthMock, human.getDateOfBirth());
        assertEquals(SkillLevel.ADVANCED, human.getSkillLevel());
        assertTrue(human.getSkillLevel().compareTo(SkillLevel.BASIC) > 0);

        verify(dateOfBirthMock);
    }

    @Test public void testSetResidence() {
        Calendar dateOfBirthMock = createMock(GregorianCalendar.class);
        ResidenceStructure residenceMock = createMock(ResidenceStructure.class);

        replay(dateOfBirthMock, residenceMock);

        HumanAgent human = new HumanAgentImpl(dateOfBirthMock, SkillLevel.ADVANCED);
        human.setResidence(residenceMock);

        assertSame(residenceMock, human.getResidence());

        verify(dateOfBirthMock, residenceMock);
    }

    @Test public void testSetJob() {
        Calendar dateOfBirthMock = createMock(GregorianCalendar.class);
        Job jobMock = createMock(Job.class);

        replay(dateOfBirthMock, jobMock);

        HumanAgent worker = new HumanAgentImpl(dateOfBirthMock, SkillLevel.ADVANCED);
        worker.setJob(jobMock);

        assertSame(jobMock, worker.getJob());

        verify(dateOfBirthMock, jobMock);
    }
}
