package com.urban.simengine;

import org.junit.Test;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;
import org.easymock.IMocksControl;
import static org.easymock.EasyMock.createControl;
import static org.hamcrest.Matchers.*;

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

    @Test public void testGetParentsChildren() {
        IMocksControl control = createControl();

        HumanAgent parentMock1 = control.createMock(HumanAgent.class);
        HumanAgent parentMock2 = control.createMock(HumanAgent.class);
        HumanAgent childMock1 = control.createMock(HumanAgent.class);

        Set<HumanAgent> parentsSet = new HashSet<HumanAgent>();
        parentsSet.add(parentMock1);
        parentsSet.add(parentMock2);

        Set<HumanAgent> childrenSet = new HashSet<HumanAgent>();
        childrenSet.add(childMock1);

        Family family = new FamilyImpl();
        family.getMembers().add(parentMock1);
        family.getMembers().add(parentMock2);
        family.getMembers().add(childMock1);

        expect(parentMock1.getParents()).andReturn(new HashSet<HumanAgent>()).anyTimes();
        expect(parentMock1.getChildren()).andReturn(childrenSet).anyTimes();
        expect(parentMock1.getFamily()).andReturn(family).anyTimes();
        expect(parentMock2.getParents()).andReturn(new HashSet<HumanAgent>()).anyTimes();
        expect(parentMock2.getChildren()).andReturn(childrenSet).anyTimes();
        expect(parentMock2.getFamily()).andReturn(family).anyTimes();
        expect(childMock1.getParents()).andReturn(parentsSet).anyTimes();
        expect(childMock1.getChildren()).andReturn(new HashSet<HumanAgent>()).anyTimes();
        expect(childMock1.getFamily()).andReturn(family).anyTimes();

        control.replay();

        Set<HumanAgent> actualParents = family.getParents();
        Set<HumanAgent> actualChildren = family.getChildren();

        control.verify();

        assertThat(actualParents.size(), equalTo(2));
        assertThat(actualParents, hasItem(parentMock1));
        assertThat(actualParents, hasItem(parentMock2));

        assertThat(actualChildren.size(), equalTo(1));
        assertThat(actualChildren, hasItem(childMock1));
    }
}
