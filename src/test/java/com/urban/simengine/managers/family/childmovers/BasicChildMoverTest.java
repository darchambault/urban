package com.urban.simengine.managers.family.childmovers;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.easymock.EasyMock.*;
import org.easymock.IMocksControl;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.powermock.api.easymock.PowerMock.expectPrivate;

import com.urban.simengine.Family;
import com.urban.simengine.Gender;
import com.urban.simengine.agents.HumanAgent;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RunWith(PowerMockRunner.class)
@PrepareForTest(BasicChildMover.class)
public class BasicChildMoverTest {
    @Test public void testNoChildrenMovedOutIfNoFamilies() {
        IMocksControl control = createControl();

        Calendar currentTimeMock = control.createMock(Calendar.class);

        Set<Family> families = new HashSet<Family>();

        control.replay();

        ChildMover mover = new BasicChildMover();
        Set<HumanAgent> newAdults = mover.moveOutAdultChildren(families, currentTimeMock);

        assertEquals(0, newAdults.size());

        control.verify();
    }

    @Test public void testNoChildrenMovedOutIfNoChildrenInFamily() throws Exception {
        IMocksControl control = createControl();

        Calendar currentTimeMock = control.createMock(Calendar.class);

        HumanAgent humanMock1 = this.getHumanMock(control, Gender.MALE, 40, null);
        HumanAgent humanMock2 = this.getHumanMock(control, Gender.FEMALE, 40, null);

        Family familyMock1 = control.createMock(Family.class);

        this.mockupFamily(familyMock1, new HumanAgent[]{humanMock1, humanMock2});

        Set<Family> families = new HashSet<Family>();
        families.add(familyMock1);

        control.replay();

        ChildMover mover = new BasicChildMover();
        Set<HumanAgent> newAdults = mover.moveOutAdultChildren(families, currentTimeMock);

        assertEquals(0, newAdults.size());

        control.verify();
    }

    @Test public void testNoChildrenMovedOutIfTooYoung() throws Exception {
        IMocksControl control = createControl();

        Calendar currentTimeMock = control.createMock(Calendar.class);

        HumanAgent humanMock1 = this.getHumanMock(control, Gender.MALE, 40, null);
        HumanAgent humanMock2 = this.getHumanMock(control, Gender.FEMALE, 40, null);
        HumanAgent humanMock3 = this.getHumanMock(control, Gender.MALE, 14, new HumanAgent[] {humanMock1, humanMock2});

        Family familyMock1 = control.createMock(Family.class);

        this.mockupFamily(familyMock1, new HumanAgent[]{humanMock1, humanMock2, humanMock3});

        Set<Family> families = new HashSet<Family>();
        families.add(familyMock1);

        control.replay();

        ChildMover mover = new BasicChildMover();
        Set<HumanAgent> newAdults = mover.moveOutAdultChildren(families, currentTimeMock);

        assertEquals(0, newAdults.size());

        control.verify();
    }

    @Test public void testChildReadyToMoveOutIsMovedOut() throws Exception {
        IMocksControl control = createControl();

        Calendar currentTimeMock = control.createMock(Calendar.class);

        HumanAgent humanMock1 = this.getHumanMock(control, Gender.MALE, 40, null);
        HumanAgent humanMock2 = this.getHumanMock(control, Gender.FEMALE, 40, null);
        HumanAgent humanMock3 = this.getHumanMock(control, Gender.MALE, 18, new HumanAgent[] {humanMock1, humanMock2});

        Family familyMock1 = control.createMock(Family.class);
        Family familyMock2 = control.createMock(Family.class);

        this.mockupFamily(familyMock1, new HumanAgent[]{humanMock1, humanMock2, humanMock3});

        this.expectFamilyCreation(humanMock3, familyMock2);

        Set<Family> families = new HashSet<Family>();
        families.add(familyMock1);

        control.replay();

        ChildMover mover = PowerMock.createPartialMock(BasicChildMover.class, "getNewFamilyInstance");
        expectPrivate(mover, "getNewFamilyInstance").andReturn(familyMock2);

        PowerMock.replay(mover);

        Set<HumanAgent> newAdults = mover.moveOutAdultChildren(families, currentTimeMock);

        assertEquals(1, newAdults.size());
        assertTrue(newAdults.contains(humanMock3));

        control.verify();
        PowerMock.verify(mover);
    }

    @Test public void testChildMovedOutDeep() throws Exception {
        IMocksControl control = createControl();

        Calendar currentTimeMock = control.createMock(Calendar.class);

        HumanAgent humanMock1 = this.getHumanMock(control, Gender.MALE, 40, null);
        HumanAgent humanMock2 = this.getHumanMock(control, Gender.FEMALE, 40, null);
        HumanAgent humanMock3 = this.getHumanMock(control, Gender.MALE, 18, new HumanAgent[] {humanMock1, humanMock2});

        Family familyMock1 = control.createMock(Family.class);

        this.mockupFamily(familyMock1, new HumanAgent[]{humanMock1, humanMock2, humanMock3});

        Set<Family> families = new HashSet<Family>();
        families.add(familyMock1);

        expect(humanMock3.setFamily(anyObject(Family.class))).andReturn(humanMock3).once();

        control.replay();

        ChildMover mover = new BasicChildMover();
        Set<HumanAgent> newAdults = mover.moveOutAdultChildren(families, currentTimeMock);

        assertEquals(1, newAdults.size());

        control.verify();
    }

    private HumanAgent getHumanMock(IMocksControl control, Gender gender, int age, HumanAgent[] parents) {
        HumanAgent humanMock = control.createMock(HumanAgent.class);
        expect(humanMock.getGender()).andReturn(gender).anyTimes();
        expect(humanMock.getAge(anyObject(Calendar.class))).andReturn(age).anyTimes();
        Set<HumanAgent> parentsSet = new HashSet<HumanAgent>();
        if (parents != null) {
            Collections.addAll(parentsSet, parents);
        }
        expect(humanMock.getParents()).andReturn(parentsSet).anyTimes();
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

    private void expectFamilyCreation(HumanAgent human, Family newFamily) {
        expect(human.setFamily(newFamily)).andReturn(human).once();
        expect(newFamily.getMembers()).andReturn(new HashSet<HumanAgent>()).once();
    }
}
