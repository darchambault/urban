package com.urban.simengine.agents;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.createControl;
import org.easymock.IMocksControl;
import static org.hamcrest.Matchers.*;

import com.urban.simengine.Family;
import com.urban.simengine.Gender;
import com.urban.simengine.Job;
import com.urban.simengine.SkillLevel;

import java.util.*;

public class HumanAgentImplTest {
    @Test public void testConstructor() {
        IMocksControl control = createControl();

        String firstName = "John";
        String lastName = "Doe";

        Calendar dateOfBirthMock = control.createMock(GregorianCalendar.class);

        Family familyMock = control.createMock(Family.class);

        Set<HumanAgent> parents = new HashSet<HumanAgent>();
        HumanAgent parentMock1 = control.createMock(HumanAgent.class);
        HumanAgent parentMock2 = control.createMock(HumanAgent.class);
        parents.add(parentMock1);
        parents.add(parentMock2);

        Set<HumanAgent> children = new HashSet<HumanAgent>();
        HumanAgent childMock = control.createMock(HumanAgent.class);
        children.add(childMock);

        control.replay();

        HumanAgent human = new HumanAgentImpl(dateOfBirthMock, Gender.MALE, firstName, lastName, parents, children, familyMock, SkillLevel.ADVANCED);

        assertSame(dateOfBirthMock, human.getDateOfBirth());
        assertEquals(Gender.MALE, human.getGender());
        assertEquals(firstName, human.getFirstName());
        assertEquals(lastName, human.getLastName());
        assertEquals(2, human.getParents().size());
        assertTrue(human.getParents().contains(parentMock1));
        assertTrue(human.getParents().contains(parentMock2));
        assertEquals(1, human.getChildren().size());
        assertTrue(human.getChildren().contains(childMock));
        assertSame(familyMock, human.getFamily());
        assertEquals(SkillLevel.ADVANCED, human.getSkillLevel());
        assertTrue(human.getSkillLevel().compareTo(SkillLevel.BASIC) > 0);
        assertThat(human.toString(), startsWith("John Doe #"));

        control.verify();
    }

    @Test public void testGetAgeDeep() {
        IMocksControl control = createControl();

        Calendar dateOfBirth = new GregorianCalendar(2000, 1, 1);
        Calendar currentDate = new GregorianCalendar(2020, 1, 1);

        Family familyMock = control.createMock(Family.class);

        control.replay();

        HumanAgent human = new HumanAgentImpl(dateOfBirth, Gender.MALE, "John", "Doe", null, null, familyMock, SkillLevel.ADVANCED);
        assertEquals(20, human.getAge(currentDate));

        control.verify();
    }

    @Test public void testSetFamily() {
        IMocksControl control = createControl();

        Calendar dateOfBirthMock = control.createMock(GregorianCalendar.class);
        Family familyMock1 = control.createMock(Family.class);
        Family familyMock2 = control.createMock(Family.class);

        control.replay();

        HumanAgent human = new HumanAgentImpl(dateOfBirthMock, Gender.MALE, "John", "Doe", null, null, familyMock1, SkillLevel.ADVANCED);
        human.setFamily(familyMock2);

        assertSame(familyMock2, human.getFamily());

        control.verify();
    }

    @Test public void testSetSkillLevel() {
        IMocksControl control = createControl();

        Calendar dateOfBirthMock = control.createMock(GregorianCalendar.class);
        Family familyMock = control.createMock(Family.class);

        control.replay();

        HumanAgent human = new HumanAgentImpl(dateOfBirthMock, Gender.MALE, "John", "Doe", null, null, familyMock, SkillLevel.ADVANCED);
        human.setSkillLevel(SkillLevel.BASIC);

        assertEquals(SkillLevel.BASIC, human.getSkillLevel());

        control.verify();
    }

    @Test public void testSetJob() {
        IMocksControl control = createControl();

        Calendar dateOfBirthMock = control.createMock(GregorianCalendar.class);
        Family familyMock = control.createMock(Family.class);
        Job jobMock = control.createMock(Job.class);

        control.replay();

        HumanAgent worker = new HumanAgentImpl(dateOfBirthMock, Gender.MALE, "John", "Doe", null, null, familyMock, SkillLevel.ADVANCED);
        worker.setJob(jobMock);

        assertSame(jobMock, worker.getJob());

        control.verify();
    }
}
