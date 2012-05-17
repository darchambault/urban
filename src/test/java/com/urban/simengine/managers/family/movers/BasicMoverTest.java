package com.urban.simengine.managers.family.movers;

import org.junit.Test;
import org.easymock.IMocksControl;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import com.urban.simengine.Family;
import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.structures.ResidenceStructure;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class BasicMoverTest {
    @Test public void testNoFamiliesMovedInIfNoFamilies() {
        Set<Family> families = new HashSet<Family>();
        Set<ResidenceStructure> residences = new HashSet<ResidenceStructure>();

        Mover mover = new BasicMover();
        Set<Family> familiesMovedIn = mover.moveIn(families, residences);

        assertThat(familiesMovedIn.size(), equalTo(0));
    }

    @Test public void testNoFamiliesMovedInIfNoResidences() {
        IMocksControl control = createControl();

        Set<Family> families = new HashSet<Family>();
        HumanAgent humanMock = control.createMock(HumanAgent.class);
        Family family = control.createMock(Family.class);
        this.mockupHomelessFamily(family, new HumanAgent[] {humanMock});

        Set<ResidenceStructure> residences = new HashSet<ResidenceStructure>();

        control.replay();

        Mover mover = new BasicMover();
        Set<Family> familiesMovedIn = mover.moveIn(families, residences);

        assertThat(familiesMovedIn.size(), equalTo(0));

        control.verify();
    }

    @Test public void testFamilyMovedIn() {
        IMocksControl control = createControl();

        Set<Family> residentFamiliesMock = control.createMock(Set.class);

        Set<Family> families = new HashSet<Family>();
        HumanAgent humanMock = control.createMock(HumanAgent.class);
        Family familyMock = control.createMock(Family.class);
        this.mockupHomelessFamily(familyMock, new HumanAgent[] {humanMock});
        families.add(familyMock);

        Set<ResidenceStructure> residences = new HashSet<ResidenceStructure>();
        ResidenceStructure residenceMock = control.createMock(ResidenceStructure.class);
        residences.add(residenceMock);
        this.mockupResidenceWithVacancy(residenceMock, residentFamiliesMock);

        this.expectResidenceAssignment(residenceMock, residentFamiliesMock, familyMock);

        control.replay();

        Mover mover = new BasicMover();
        Set<Family> familiesMovedIn = mover.moveIn(families, residences);

        assertThat(familiesMovedIn.size(), equalTo(1));

        control.verify();
    }

    @Test public void testFamilyMovedInWithTwoFamiliesOneSpot() {
        IMocksControl control = createControl();

        Set<Family> residentFamiliesMock = control.createMock(Set.class);

        Set<Family> families = new LinkedHashSet<Family>();

        HumanAgent humanMock1 = control.createMock(HumanAgent.class);
        Family familyMock1 = control.createMock(Family.class);
        this.mockupHomelessFamily(familyMock1, new HumanAgent[] {humanMock1});
        families.add(familyMock1);
        HumanAgent humanMock2 = control.createMock(HumanAgent.class);
        Family familyMock2 = control.createMock(Family.class);
        this.mockupHomelessFamily(familyMock2, new HumanAgent[]{humanMock2});
        families.add(familyMock2);

        Set<ResidenceStructure> residences = new HashSet<ResidenceStructure>();
        ResidenceStructure residenceMock = control.createMock(ResidenceStructure.class);
        residences.add(residenceMock);
        this.mockupResidenceWithVacancy(residenceMock, residentFamiliesMock);

        this.expectResidenceAssignment(residenceMock, residentFamiliesMock, familyMock1);

        this.mockupResidenceNoVacancy(residenceMock, residentFamiliesMock);

        control.replay();

        Mover mover = new BasicMover();
        Set<Family> familiesMovedIn = mover.moveIn(families, residences);

        assertThat(familiesMovedIn.size(), equalTo(1));
        assertThat(familiesMovedIn, hasItem(familyMock1));

        control.verify();
    }

    @Test public void testFamilyMovedInWithTwoFamiliesTwoSpots() {
        IMocksControl control = createControl();

        Set<Family> residentFamiliesMock1 = control.createMock(Set.class);
        Set<Family> residentFamiliesMock2 = control.createMock(Set.class);

        Set<Family> families = new LinkedHashSet<Family>();

        HumanAgent humanMock1 = control.createMock(HumanAgent.class);
        Family familyMock1 = control.createMock(Family.class);
        this.mockupHomelessFamily(familyMock1, new HumanAgent[] {humanMock1});
        families.add(familyMock1);
        HumanAgent humanMock2 = control.createMock(HumanAgent.class);
        Family familyMock2 = control.createMock(Family.class);
        this.mockupHomelessFamily(familyMock2, new HumanAgent[]{humanMock2});
        families.add(familyMock2);

        Set<ResidenceStructure> residences = new LinkedHashSet<ResidenceStructure>();

        ResidenceStructure residenceMock1 = control.createMock(ResidenceStructure.class);
        residences.add(residenceMock1);
        ResidenceStructure residenceMock2 = control.createMock(ResidenceStructure.class);
        residences.add(residenceMock2);

        this.mockupResidenceWithVacancy(residenceMock1, residentFamiliesMock1);
        this.expectResidenceAssignment(residenceMock1, residentFamiliesMock1, familyMock1);
        this.mockupResidenceNoVacancy(residenceMock1, residentFamiliesMock1);
        this.mockupResidenceWithVacancy(residenceMock2, residentFamiliesMock2);
        this.expectResidenceAssignment(residenceMock2, residentFamiliesMock2, familyMock2);

        control.replay();

        Mover mover = new BasicMover();
        Set<Family> familiesMovedIn = mover.moveIn(families, residences);

        assertThat(familiesMovedIn.size(), equalTo(2));
        assertThat(familiesMovedIn, hasItem(familyMock1));
        assertThat(familiesMovedIn, hasItem(familyMock2));

        control.verify();
    }

    private void expectResidenceAssignment(ResidenceStructure residenceMock, Set<Family> residentFamiliesMock, Family familyMock) {
        expect(familyMock.setResidence(residenceMock)).andReturn(familyMock).once();
        expect(residenceMock.getFamilies()).andReturn(residentFamiliesMock).once();
        expect(residentFamiliesMock.add(familyMock)).andReturn(true).once();
    }

    private void mockupResidenceWithVacancy(ResidenceStructure residenceMock, Set<Family> residentFamiliesMock) {
        expect(residenceMock.getFamilies()).andReturn(residentFamiliesMock).once();
        expect(residenceMock.getMaximumFamilies()).andReturn(1).once();
        expect(residentFamiliesMock.size()).andReturn(0).once();
    }

    private void mockupResidenceNoVacancy(ResidenceStructure residenceMock, Set<Family> residentFamiliesMock) {
        expect(residenceMock.getFamilies()).andReturn(residentFamiliesMock).once();
        expect(residenceMock.getMaximumFamilies()).andReturn(1).once();
        expect(residentFamiliesMock.size()).andReturn(1).once();
    }

    private void mockupHomelessFamily(Family family, HumanAgent[] humanMocks) {
        Set<HumanAgent> familyHumans = new HashSet<HumanAgent>();
        for (HumanAgent humanMock : humanMocks) {
            familyHumans.add(humanMock);
            expect(humanMock.getFamily()).andReturn(family).anyTimes();
        }
        expect(family.getMembers()).andReturn(familyHumans).anyTimes();
        expect(family.getResidence()).andReturn(null).anyTimes();
    }
}
