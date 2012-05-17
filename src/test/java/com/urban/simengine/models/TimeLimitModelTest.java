package com.urban.simengine.models;

import com.google.common.eventbus.EventBus;
import com.urban.simengine.Family;
import com.urban.simengine.managers.family.FamilyManager;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;
import org.easymock.IMocksControl;
import static org.hamcrest.Matchers.*;

import com.urban.simengine.managers.time.TimeManager;
import com.urban.simengine.managers.population.PopulationManager;
import com.urban.simengine.structures.ResidenceStructure;
import com.urban.simengine.structures.WorkStructure;
import com.urban.simengine.Job;
import com.urban.simengine.agents.HumanAgent;

import java.util.*;

public class TimeLimitModelTest  {
    @Test public void testConstructor() {
        IMocksControl control = createControl();

        EventBus eventBusMock = control.createMock(EventBus.class);

        GregorianCalendar endDateMock = control.createMock(GregorianCalendar.class);

        Set<ResidenceStructure> residences = new HashSet<ResidenceStructure>();
        ResidenceStructure residenceMock1 = control.createMock(ResidenceStructure.class);
        residences.add(residenceMock1);
        ResidenceStructure residenceMock2 = control.createMock(ResidenceStructure.class);
        residences.add(residenceMock2);

        Set<WorkStructure> workplaces = new HashSet<WorkStructure>();
        WorkStructure workplaceMock1 = control.createMock(WorkStructure.class);
        workplaces.add(workplaceMock1);
        WorkStructure workplaceMock2 = control.createMock(WorkStructure.class);
        workplaces.add(workplaceMock2);

        TimeManager timeManagerMock = control.createMock(TimeManager.class);
        PopulationManager populationManagerMock = control.createMock(PopulationManager.class);
        FamilyManager familyManagerMock = control.createMock(FamilyManager.class);

        control.replay();

        TimeLimitModel model = new TimeLimitModel(eventBusMock, timeManagerMock, endDateMock, populationManagerMock, familyManagerMock, residences, workplaces);

        assertSame(eventBusMock, model.getEventBus());
        assertSame(timeManagerMock, model.getTimeManager());
        assertSame(endDateMock, model.getEndDate());
        assertSame(populationManagerMock, model.getPopulationManager());
        assertSame(familyManagerMock, model.getFamilyManager());
        assertTrue(model.getResidences().contains(residenceMock1));
        assertTrue(model.getResidences().contains(residenceMock2));
        assertTrue(model.getWorkplaces().contains(workplaceMock1));
        assertTrue(model.getWorkplaces().contains(workplaceMock2));

        control.verify();
    }

    @Test public void testGetUnfilledJobs() {
        IMocksControl control = createControl();

        EventBus eventBusMock = control.createMock(EventBus.class);
        GregorianCalendar endDateMock = control.createMock(GregorianCalendar.class);
        TimeManager timeManagerMock = control.createMock(TimeManager.class);
        PopulationManager populationManagerMock = control.createMock(PopulationManager.class);
        FamilyManager familyManagerMock = control.createMock(FamilyManager.class);
        Set<ResidenceStructure> residences = new HashSet<ResidenceStructure>();

        Set<WorkStructure> workplaces = new HashSet<WorkStructure>();
        WorkStructure workplaceMock1 = control.createMock(WorkStructure.class);
        workplaces.add(workplaceMock1);
        WorkStructure workplaceMock2 = control.createMock(WorkStructure.class);
        workplaces.add(workplaceMock2);

        Set<Job> workplace1Jobs = new HashSet<Job>();
        Set<Job> workplace2Jobs = new HashSet<Job>();

        Job jobMock1 = control.createMock(Job.class);
        workplace1Jobs.add(jobMock1);
        Job jobMock2 = control.createMock(Job.class);
        workplace1Jobs.add(jobMock2);
        Job jobMock3 = control.createMock(Job.class);
        workplace2Jobs.add(jobMock3);

        expect(workplaceMock1.getJobs()).andReturn(workplace1Jobs).atLeastOnce();
        expect(jobMock1.getHuman()).andReturn(control.createMock(HumanAgent.class)).atLeastOnce();
        expect(jobMock2.getHuman()).andReturn(null).atLeastOnce();
        expect(workplaceMock2.getJobs()).andReturn(workplace2Jobs).atLeastOnce();
        expect(jobMock3.getHuman()).andReturn(null).atLeastOnce();

        control.replay();

        TimeLimitModel model = new TimeLimitModel(eventBusMock, timeManagerMock, endDateMock, populationManagerMock, familyManagerMock, residences, workplaces);
        Set<Job> unfilledJobs = model.getUnfilledJobs();

        assertEquals(2, unfilledJobs.size());
        assertTrue(unfilledJobs.contains(jobMock2));
        assertTrue(unfilledJobs.contains(jobMock3));

        control.verify();
    }

    @Test public void testGetResidencesWithVacancy() {
        IMocksControl control = createControl();

        EventBus eventBusMock = control.createMock(EventBus.class);
        GregorianCalendar endDateMock = control.createMock(GregorianCalendar.class);
        TimeManager timeManagerMock = control.createMock(TimeManager.class);
        PopulationManager populationManagerMock = control.createMock(PopulationManager.class);
        FamilyManager familyManagerMock = control.createMock(FamilyManager.class);
        Set<ResidenceStructure> residences = new HashSet<ResidenceStructure>();
        Set<WorkStructure> workplaces = new HashSet<WorkStructure>();

        ResidenceStructure residenceMock1 = control.createMock(ResidenceStructure.class);
        residences.add(residenceMock1);
        Set<Family> residentFamiliesMock1 = control.createMock(Set.class);
        expect(residenceMock1.getMaximumFamilies()).andReturn(1).anyTimes();
        expect(residenceMock1.getFamilies()).andReturn(residentFamiliesMock1).anyTimes();
        expect(residentFamiliesMock1.size()).andReturn(0).anyTimes();

        ResidenceStructure residenceMock2 = control.createMock(ResidenceStructure.class);
        residences.add(residenceMock2);
        Set<Family> residentFamiliesMock2 = control.createMock(Set.class);
        expect(residenceMock2.getMaximumFamilies()).andReturn(2).anyTimes();
        expect(residenceMock2.getFamilies()).andReturn(residentFamiliesMock2).anyTimes();
        expect(residentFamiliesMock2.size()).andReturn(2).anyTimes();

        ResidenceStructure residenceMock3 = control.createMock(ResidenceStructure.class);
        residences.add(residenceMock3);
        Set<Family> residentFamiliesMock3 = control.createMock(Set.class);
        expect(residenceMock3.getMaximumFamilies()).andReturn(2).anyTimes();
        expect(residenceMock3.getFamilies()).andReturn(residentFamiliesMock3).anyTimes();
        expect(residentFamiliesMock3.size()).andReturn(1).anyTimes();

        control.replay();

        TimeLimitModel model = new TimeLimitModel(eventBusMock, timeManagerMock, endDateMock, populationManagerMock, familyManagerMock, residences, workplaces);

        Set<ResidenceStructure> residencesWithVacancy = model.getResidencesWithVacancy();

        assertThat(residencesWithVacancy.size(), equalTo(2));
        assertThat(residencesWithVacancy, hasItem(residenceMock1));
        assertThat(residencesWithVacancy, hasItem(residenceMock3));

        control.verify();
    }

    @Test public void testProcessTick() {
        IMocksControl control = createControl();

        EventBus eventBusMock = control.createMock(EventBus.class);
        GregorianCalendar endDateMock = control.createMock(GregorianCalendar.class);

        Set<ResidenceStructure> residences = new HashSet<ResidenceStructure>();
        Set<WorkStructure> workplaces = new HashSet<WorkStructure>();

        TimeManager timeManagerMock = control.createMock(TimeManager.class);
        PopulationManager populationManagerMock = control.createMock(PopulationManager.class);
        FamilyManager familyManagerMock = control.createMock(FamilyManager.class);
        Calendar currentDateMock = control.createMock(Calendar.class);

        expect(timeManagerMock.getCurrentDate()).andReturn(currentDateMock).anyTimes();
        expect(timeManagerMock.getTickLength()).andReturn(1).anyTimes();
        expect(timeManagerMock.getTickLengthUnit()).andReturn(Calendar.MONTH).anyTimes();

        Set<Job> unfilledJobs = new HashSet<Job>();

        TimeLimitModel model = new TimeLimitModel(eventBusMock, timeManagerMock, endDateMock, populationManagerMock, familyManagerMock, residences, workplaces);

        populationManagerMock.processTick(currentDateMock, 1, Calendar.MONTH, unfilledJobs);
        expectLastCall().once();

        familyManagerMock.processTick(currentDateMock, residences);
        expectLastCall().once();

        control.replay();

        model.processTick();

        control.verify();
    }

    @Test public void testIsCompleteFalse() {
        IMocksControl control = createControl();

        EventBus eventBusMock = control.createMock(EventBus.class);
        GregorianCalendar endDateMock = control.createMock(GregorianCalendar.class);

        Set<ResidenceStructure> residences = new HashSet<ResidenceStructure>();
        Set<WorkStructure> workplaces = new HashSet<WorkStructure>();


        GregorianCalendar firstDateMock = control.createMock(GregorianCalendar.class);
        expect(firstDateMock.compareTo(endDateMock)).andReturn(-1).once();
        TimeManager timeManagerMock = control.createMock(TimeManager.class);
        expect(timeManagerMock.getCurrentDate()).andReturn(firstDateMock).once();

        PopulationManager populationManagerMock = createMock(PopulationManager.class);
        FamilyManager familyManagerMock = control.createMock(FamilyManager.class);

        control.replay();

        TimeLimitModel model = new TimeLimitModel(eventBusMock, timeManagerMock, endDateMock, populationManagerMock, familyManagerMock, residences, workplaces);
        assertFalse(model.isComplete());

        control.verify();
    }


    @Test public void testIsCompleteTrue() {
        IMocksControl control = createControl();

        EventBus eventBusMock = control.createMock(EventBus.class);
        GregorianCalendar endDateMock = control.createMock(GregorianCalendar.class);

        Set<ResidenceStructure> residences = new HashSet<ResidenceStructure>();
        Set<WorkStructure> workplaces = new HashSet<WorkStructure>();

        GregorianCalendar firstDateMock = control.createMock(GregorianCalendar.class);
        expect(firstDateMock.compareTo(endDateMock)).andReturn(0).once();
        TimeManager timeManagerMock = control.createMock(TimeManager.class);
        expect(timeManagerMock.getCurrentDate()).andReturn(firstDateMock).once();

        PopulationManager populationManagerMock = createMock(PopulationManager.class);
        FamilyManager familyManagerMock = control.createMock(FamilyManager.class);

        control.replay();

        TimeLimitModel model = new TimeLimitModel(eventBusMock, timeManagerMock, endDateMock, populationManagerMock, familyManagerMock, residences, workplaces);
        assertTrue(model.isComplete());

        control.verify();
    }
}
