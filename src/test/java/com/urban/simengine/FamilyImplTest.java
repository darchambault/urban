package com.urban.simengine;

import org.junit.Test;
import static org.junit.Assert.*;
import org.easymock.IMocksControl;
import static org.easymock.EasyMock.createControl;

import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.structures.ResidenceStructure;

import java.util.HashSet;
import java.util.Set;

public class FamilyImplTest {
    @Test public void testConstructor() {
        IMocksControl control = createControl();

        control.replay();

        Family family = new FamilyImpl();

        assertEquals(0, family.getMembers().size());
        assertEquals(null, family.getResidence());

        control.verify();
    }

    @Test public void testConstructorWithParams() {
        IMocksControl control = createControl();

        Set<HumanAgent> members = new HashSet<HumanAgent>();
        HumanAgent humanMock1 = control.createMock(HumanAgent.class);
        members.add(humanMock1);
        HumanAgent humanMock2 = control.createMock(HumanAgent.class);
        members.add(humanMock2);

        ResidenceStructure residenceMock = control.createMock(ResidenceStructure.class);

        control.replay();

        Family family = new FamilyImpl(members, residenceMock);

        assertEquals(2, family.getMembers().size());
        assertTrue(family.getMembers().contains(humanMock1));
        assertTrue(family.getMembers().contains(humanMock2));
        assertSame(residenceMock, family.getResidence());

        control.verify();
    }

    @Test public void testAddMember() {
        IMocksControl control = createControl();

        HumanAgent humanMock = control.createMock(HumanAgent.class);

        control.replay();

        Family family = new FamilyImpl();
        family.getMembers().add(humanMock);

        assertEquals(1, family.getMembers().size());
        assertTrue(family.getMembers().contains(humanMock));

        control.verify();
    }

    @Test public void testSetResidence() {
        IMocksControl control = createControl();

        ResidenceStructure residenceMock = control.createMock(ResidenceStructure.class);

        control.replay();

        Family family = new FamilyImpl();
        family.setResidence(residenceMock);

        assertSame(residenceMock, family.getResidence());

        control.verify();
    }
}
