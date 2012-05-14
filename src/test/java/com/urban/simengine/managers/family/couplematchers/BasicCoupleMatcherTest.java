package com.urban.simengine.managers.family.couplematchers;

import org.junit.Test;
import org.easymock.IMocksControl;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.urban.simengine.Family;
import com.urban.simengine.Gender;
import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.structures.ResidenceStructure;

import java.util.HashSet;
import java.util.Set;

public class BasicCoupleMatcherTest {
    @Test public void testCreateCouplesWithNoHumans() {
        IMocksControl control = createControl();

        Set<Family> families = new HashSet<Family>();

        control.replay();

        CoupleMatcher matcher = new BasicCoupleMatcher();
        Set<Family> newFamilies = matcher.createCouples(families);

        assertEquals(0, newFamilies.size());

        control.verify();
    }

    @Test public void testCreateCouplesWithMixedSet() {
        IMocksControl control = createControl();

        HumanAgent humanMock1 = this.getHumanMock(control, Gender.MALE);
        HumanAgent humanMock2 = this.getHumanMock(control, Gender.MALE);
        HumanAgent humanMock3 = this.getHumanMock(control, Gender.FEMALE);
        HumanAgent humanMock4 = this.getHumanMock(control, Gender.FEMALE);

        Family family1 = control.createMock(Family.class);
        Family family2 = control.createMock(Family.class);
        Family family3 = control.createMock(Family.class);

        this.mockupFamily(family1, new HumanAgent[] {humanMock1, humanMock4});
        this.mockupFamily(family2, new HumanAgent[] {humanMock2});
        this.mockupFamily(family3, new HumanAgent[] {humanMock3});

        this.expectFamilyMerge(control, humanMock3, family3, family2);

        Set<Family> families = new HashSet<Family>();
        families.add(family1);
        families.add(family2);
        families.add(family3);

        control.replay();

        CoupleMatcher matcher = new BasicCoupleMatcher();
        Set<Family> newFamilies = matcher.createCouples(families);

        assertEquals(1, newFamilies.size());
        assertTrue(newFamilies.contains(family2));

        control.verify();
    }

    @Test public void testCreateCouplesWithNoSingles() {
        IMocksControl control = createControl();

        HumanAgent humanMock1 = this.getHumanMock(control, Gender.MALE);
        HumanAgent humanMock2 = this.getHumanMock(control, Gender.MALE);
        HumanAgent humanMock3 = this.getHumanMock(control, Gender.FEMALE);
        HumanAgent humanMock4 = this.getHumanMock(control, Gender.FEMALE);

        Family family1 = control.createMock(Family.class);
        Family family2 = control.createMock(Family.class);

        this.mockupFamily(family1, new HumanAgent[] {humanMock1, humanMock4});
        this.mockupFamily(family2, new HumanAgent[] {humanMock2, humanMock3});

        Set<Family> families = new HashSet<Family>();
        families.add(family1);
        families.add(family2);

        control.replay();

        CoupleMatcher matcher = new BasicCoupleMatcher();
        Set<Family> newFamilies = matcher.createCouples(families);

        assertEquals(0, newFamilies.size());

        control.verify();
    }

    @Test public void testCreateCouplesWithNoMatches() {
        IMocksControl control = createControl();

        HumanAgent humanMock1 = this.getHumanMock(control, Gender.MALE);
        HumanAgent humanMock2 = this.getHumanMock(control, Gender.MALE);
        HumanAgent humanMock3 = this.getHumanMock(control, Gender.FEMALE);
        HumanAgent humanMock4 = this.getHumanMock(control, Gender.MALE);

        Family family1 = control.createMock(Family.class);
        Family family2 = control.createMock(Family.class);
        Family family3 = control.createMock(Family.class);

        this.mockupFamily(family1, new HumanAgent[] {humanMock1});
        this.mockupFamily(family2, new HumanAgent[] {humanMock2, humanMock3});
        this.mockupFamily(family3, new HumanAgent[] {humanMock4});

        Set<Family> families = new HashSet<Family>();
        families.add(family1);
        families.add(family2);
        families.add(family3);

        control.replay();

        CoupleMatcher matcher = new BasicCoupleMatcher();
        Set<Family> newFamilies = matcher.createCouples(families);

        assertEquals(0, newFamilies.size());

        control.verify();
    }

    private HumanAgent getHumanMock(IMocksControl control, Gender gender) {
        HumanAgent humanMock = control.createMock(HumanAgent.class);
        expect(humanMock.getGender()).andReturn(gender).anyTimes();
        return humanMock;
    }

    private void mockupFamily(Family family, HumanAgent[] humanMocks) {
        Set<HumanAgent> familyHumans = new HashSet<HumanAgent>();
        for (HumanAgent humanMock : humanMocks) {
            familyHumans.add(humanMock);
            expect(humanMock.getFamily()).andReturn(family).anyTimes();
        }
        expect(family.getMembers()).andReturn(familyHumans).anyTimes();
    }

    private void expectFamilyMerge(IMocksControl control, HumanAgent human, Family oldFamily, Family newFamily) {
        ResidenceStructure residenceMock = control.createMock(ResidenceStructure.class);
        Set<Family> residenceFamilies = new HashSet<Family>();
        residenceFamilies.add(oldFamily);

        expect(oldFamily.getResidence()).andReturn(residenceMock).atLeastOnce();
        expect(residenceMock.getFamilies()).andReturn(residenceFamilies).atLeastOnce();
        expect(human.setFamily(newFamily)).andReturn(human).once();
        expect(oldFamily.setResidence(null)).andReturn(oldFamily).once();
    }
}
