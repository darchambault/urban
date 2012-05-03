package com.urban.simengine.models;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;
import org.easymock.IMocksControl;

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

        PopulationManager populationManagerMock = control.createMock(PopulationManager.class);
        TimeManager timeManagerMock = control.createMock(TimeManager.class);

        control.replay();

        TimeLimitModel model = new TimeLimitModel(timeManagerMock, endDateMock, populationManagerMock, residences, workplaces);

        assertSame(populationManagerMock, model.getPopulationManager());
        assertSame(endDateMock, model.getEndDate());
        assertSame(timeManagerMock, model.getTimeManager());
        assertTrue(model.getResidences().contains(residenceMock1));
        assertTrue(model.getResidences().contains(residenceMock2));
        assertTrue(model.getWorkplaces().contains(workplaceMock1));
        assertTrue(model.getWorkplaces().contains(workplaceMock2));

        control.verify();
    }

    @Test public void testGetUnfilledJobs() {
        IMocksControl control = createControl();

        GregorianCalendar endDateMock = control.createMock(GregorianCalendar.class);
        PopulationManager populationManagerMock = control.createMock(PopulationManager.class);
        TimeManager timeManagerMock = control.createMock(TimeManager.class);
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

        TimeLimitModel model = new TimeLimitModel(timeManagerMock, endDateMock, populationManagerMock, residences, workplaces);
        Set<Job> unfilledJobs = model.getUnfilledJobs();

        assertEquals(2, unfilledJobs.size());
        assertTrue(unfilledJobs.contains(jobMock2));
        assertTrue(unfilledJobs.contains(jobMock3));

        control.verify();
    }

    @Test public void testProcessTick() {
        IMocksControl control = createControl();

        GregorianCalendar endDateMock = control.createMock(GregorianCalendar.class);

        Set<ResidenceStructure> residences = new HashSet<ResidenceStructure>();
        Set<WorkStructure> workplaces = new HashSet<WorkStructure>();

        PopulationManager populationManagerMock = control.createMock(PopulationManager.class);

        TimeManager timeManagerMock = control.createMock(TimeManager.class);

        TimeLimitModel model = new TimeLimitModel(timeManagerMock, endDateMock, populationManagerMock, residences, workplaces);

        populationManagerMock.processTick(model);
        expectLastCall().once();

        control.replay();

        model.processTick();

        control.verify();
    }

    @Test public void testIsCompleteFalse() {
        IMocksControl control = createControl();

        GregorianCalendar endDateMock = control.createMock(GregorianCalendar.class);

        Set<ResidenceStructure> residences = new HashSet<ResidenceStructure>();
        Set<WorkStructure> workplaces = new HashSet<WorkStructure>();

        PopulationManager populationManagerMock = createMock(PopulationManager.class);

        GregorianCalendar firstDateMock = control.createMock(GregorianCalendar.class);
        expect(firstDateMock.compareTo(endDateMock)).andReturn(-1).once();
        TimeManager timeManagerMock = control.createMock(TimeManager.class);
        expect(timeManagerMock.getCurrentDate()).andReturn(firstDateMock).once();

        control.replay();

        TimeLimitModel model = new TimeLimitModel(timeManagerMock, endDateMock, populationManagerMock, residences, workplaces);
        assertFalse(model.isComplete());

        control.verify();
    }


    @Test public void testIsCompleteTrue() {
        IMocksControl control = createControl();

        GregorianCalendar endDateMock = control.createMock(GregorianCalendar.class);

        Set<ResidenceStructure> residences = new HashSet<ResidenceStructure>();
        Set<WorkStructure> workplaces = new HashSet<WorkStructure>();

        PopulationManager populationManagerMock = createMock(PopulationManager.class);

        GregorianCalendar firstDateMock = control.createMock(GregorianCalendar.class);
        expect(firstDateMock.compareTo(endDateMock)).andReturn(0).once();
        TimeManager timeManagerMock = control.createMock(TimeManager.class);
        expect(timeManagerMock.getCurrentDate()).andReturn(firstDateMock).once();

        control.replay();

        TimeLimitModel model = new TimeLimitModel(timeManagerMock, endDateMock, populationManagerMock, residences, workplaces);
        assertTrue(model.isComplete());

        control.verify();
    }
}
