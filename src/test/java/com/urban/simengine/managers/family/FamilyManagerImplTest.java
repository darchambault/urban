package com.urban.simengine.managers.family;

import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.managers.family.events.FamilyMovedInEvent;
import com.urban.simengine.managers.family.movers.Mover;
import com.urban.simengine.structures.ResidenceStructure;
import org.junit.Test;
import org.easymock.IMocksControl;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.urban.simengine.Family;
import com.urban.simengine.managers.family.couplematchers.CoupleMatcher;
import com.urban.simengine.managers.family.events.CoupleCreatedEvent;
import com.urban.simengine.managers.family.childmovers.ChildMover;
import com.urban.simengine.managers.family.events.ChildMovedOutEvent;
import com.urban.simengine.managers.time.TimeManager;
import com.urban.simengine.models.Model;

import com.google.common.eventbus.EventBus;

import java.util.*;

public class FamilyManagerImplTest {
    @Test public void testConstructorWithNoHumans() {
        IMocksControl control = createControl();

        EventBus eventBusMock = control.createMock(EventBus.class);
        Mover mover = control.createMock(Mover.class);
        CoupleMatcher coupleMatcher = control.createMock(CoupleMatcher.class);
        ChildMover childMover = control.createMock(ChildMover.class);

        control.replay();

        FamilyManager manager = new FamilyManagerImpl(eventBusMock, mover, coupleMatcher, childMover);

        assertEquals(0, manager.getFamilies().size());

        control.verify();
    }

    @Test public void testAddHuman() {
        IMocksControl control = createControl();

        EventBus eventBusMock = control.createMock(EventBus.class);
        Mover mover = control.createMock(Mover.class);
        CoupleMatcher coupleMatcher = control.createMock(CoupleMatcher.class);
        ChildMover childMover = control.createMock(ChildMover.class);
        Family familyMock = control.createMock(Family.class);

        control.replay();

        FamilyManager manager = new FamilyManagerImpl(eventBusMock, mover, coupleMatcher, childMover);
        manager.getFamilies().add(familyMock);

        assertEquals(1, manager.getFamilies().size());
        assertTrue(manager.getFamilies().contains(familyMock));

        control.verify();
    }

    @Test public void testGetHomelessFamilies() {
        IMocksControl control = createControl();

        EventBus eventBusMock = control.createMock(EventBus.class);
        Mover moverMock = control.createMock(Mover.class);
        CoupleMatcher coupleMatcherMock = control.createMock(CoupleMatcher.class);
        ChildMover childMoverMock = control.createMock(ChildMover.class);

        ResidenceStructure residence = control.createMock(ResidenceStructure.class);

        Set<Family> families = new HashSet<Family>();

        Family familyMock1 = control.createMock(Family.class);
        expect(familyMock1.getResidence()).andReturn(residence).anyTimes();
        families.add(familyMock1);

        Family familyMock2 = control.createMock(Family.class);
        expect(familyMock2.getResidence()).andReturn(null).anyTimes();
        families.add(familyMock2);

        control.replay();

        FamilyManager manager = new FamilyManagerImpl(eventBusMock, moverMock, coupleMatcherMock, childMoverMock);
        for (Family family : families) {
            manager.getFamilies().add(family);
        }

        Set<Family> movedInFamilies = manager.getHomelessFamilies();

        assertEquals(1, movedInFamilies.size());
        assertTrue(movedInFamilies.contains(familyMock2));

        control.verify();
    }

    @Test public void testProcessTick() {
        IMocksControl control = createControl();

        EventBus eventBusMock = control.createMock(EventBus.class);
        Mover moverMock = control.createMock(Mover.class);
        CoupleMatcher coupleMatcherMock = control.createMock(CoupleMatcher.class);
        ChildMover childMoverMock = control.createMock(ChildMover.class);
        Model modelMock = control.createMock(Model.class);
        TimeManager timeManagerMock = control.createMock(TimeManager.class);
        Calendar currentDateMock = control.createMock(Calendar.class);

        Family familyMock1 = control.createMock(Family.class);
        Family familyMock2 = control.createMock(Family.class);
        Family familyMock3 = control.createMock(Family.class);

        Set<Family> families = new HashSet<Family>();
        families.add(familyMock1);
        families.add(familyMock2);

        Set<Family> newCouplesFamilies = new HashSet<Family>();
        newCouplesFamilies.add(familyMock1);

        expect(coupleMatcherMock.createCouples(families)).andReturn(newCouplesFamilies).once();

        eventBusMock.post(anyObject(CoupleCreatedEvent.class));
        expectLastCall().times(1);

        expect(modelMock.getTimeManager()).andReturn(timeManagerMock).anyTimes();
        expect(timeManagerMock.getCurrentDate()).andReturn(currentDateMock).anyTimes();

        Set<HumanAgent> newAdults = new HashSet<HumanAgent>();
        HumanAgent humanMock1 = control.createMock(HumanAgent.class);
        newAdults.add(humanMock1);

        expect(childMoverMock.moveOutAdultChildren(families, currentDateMock)).andReturn(newAdults).once();

        expect(humanMock1.getFamily()).andReturn(familyMock3).once();

        eventBusMock.post(anyObject(ChildMovedOutEvent.class));
        expectLastCall().times(1);

        Set<Family> movedInFamilies = new HashSet<Family>();
        movedInFamilies.add(familyMock1);

        Set<ResidenceStructure> residences = new HashSet<ResidenceStructure>();
        ResidenceStructure residenceMock = control.createMock(ResidenceStructure.class);
        residences.add(residenceMock);

        expect(moverMock.moveIn(families, residences)).andReturn(movedInFamilies).once();

        eventBusMock.post(anyObject(FamilyMovedInEvent.class));
        expectLastCall().times(1);

        control.replay();

        FamilyManager manager = new FamilyManagerImpl(eventBusMock, moverMock, coupleMatcherMock, childMoverMock);
        manager.getFamilies().add(familyMock1);
        manager.getFamilies().add(familyMock2);
        manager.processTick(currentDateMock, residences);

        control.verify();
    }
}
