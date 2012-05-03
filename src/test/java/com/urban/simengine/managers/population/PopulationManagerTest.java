package com.urban.simengine.managers.population;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;
import org.easymock.IMocksControl;

import com.urban.simengine.Job;
import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.models.Model;
import com.urban.simengine.managers.population.jobfinders.JobFinder;

import java.util.*;

public class PopulationManagerTest  {
    @Test public void testConstructorWithNoHumans() {
        JobFinder jobFinderMock = createMock(JobFinder.class);

        replay(jobFinderMock);

        PopulationManager manager = new PopulationManagerImpl(jobFinderMock);

        assertSame(jobFinderMock, manager.getJobFinder());
        assertEquals(0, manager.getHumans().size());

        verify(jobFinderMock);
    }

    @Test public void testConstructorWithHumans() {
        IMocksControl control = createControl();

        JobFinder jobFinderMock = control.createMock(JobFinder.class);

        Set<HumanAgent> humans = new HashSet<HumanAgent>();
        HumanAgent humanMock1 = control.createMock(HumanAgent.class);
        HumanAgent humanMock2 = control.createMock(HumanAgent.class);
        humans.add(humanMock1);
        humans.add(humanMock2);

        control.replay();

        PopulationManager manager = new PopulationManagerImpl(jobFinderMock, humans);

        assertSame(jobFinderMock, manager.getJobFinder());
        assertEquals(2, manager.getHumans().size());
        assertTrue(manager.getHumans().contains(humanMock1));
        assertTrue(manager.getHumans().contains(humanMock2));

        control.verify();
    }

    @Test public void testAddHuman() {
        IMocksControl control = createControl();

        JobFinder jobFinderMock = control.createMock(JobFinder.class);

        HumanAgent humanMock = createMock(HumanAgent.class);

        control.replay();

        PopulationManager manager = new PopulationManagerImpl(jobFinderMock);
        manager.addHuman(humanMock);

        assertEquals(1, manager.getHumans().size());
        assertTrue(manager.getHumans().contains(humanMock));

        control.verify();
    }

    @Test public void testGetUnemployedHumans() {
        IMocksControl control = createControl();

        JobFinder jobFinderMock = control.createMock(JobFinder.class);

        List<HumanAgent> humans = this.getHumans(control);

        control.replay();

        PopulationManager manager = new PopulationManagerImpl(jobFinderMock, new HashSet<HumanAgent>(humans));
        Set<HumanAgent> unemployedHumans = manager.getUnemployedHumans();

        assertEquals(2, unemployedHumans.size());
        assertTrue(unemployedHumans.contains(humans.get(2)));
        assertTrue(unemployedHumans.contains(humans.get(3)));

        control.verify();
    }

    @Test public void testGetEmployedHumans() {
        IMocksControl control = createControl();

        JobFinder jobFinderMock = control.createMock(JobFinder.class);

        List<HumanAgent> humans = this.getHumans(control);

        control.replay();

        PopulationManager manager = new PopulationManagerImpl(jobFinderMock, new HashSet<HumanAgent>(humans));
        Set<HumanAgent> employedHumans = manager.getEmployedHumans();

        assertEquals(2, employedHumans.size());
        assertTrue(employedHumans.contains(humans.get(0)));
        assertTrue(employedHumans.contains(humans.get(1)));

        control.verify();
    }

    @Test public void testProcessTick() {
        IMocksControl control = createControl();

        JobFinder jobFinderMock = control.createMock(JobFinder.class);

        List<HumanAgent> humans = this.getHumans(control);
        List<Job> jobs = this.getJobs(control);

        Model modelMock = control.createMock(Model.class);

        Set<Job> jobsSet = new HashSet<Job>(jobs);
        Set<HumanAgent> humansSet = new HashSet<HumanAgent>(humans);

        expect(modelMock.getUnfilledJobs()).andReturn(jobsSet).atLeastOnce();
        jobFinderMock.findJobs(humansSet, jobsSet);
        expectLastCall().atLeastOnce();

        PopulationManager manager = createMockBuilder(PopulationManagerImpl.class)
                .withConstructor(jobFinderMock, humansSet)
                .addMockedMethod("getUnemployedHumans")
                .createMock();

        expect(manager.getUnemployedHumans()).andReturn(humansSet).atLeastOnce();

        control.replay();
        replay(manager);

        manager.processTick(modelMock);

        control.verify();
        verify(manager);
    }

    private List<HumanAgent> getHumans(IMocksControl control) {
        List<HumanAgent> humans = new ArrayList<HumanAgent>();
        HumanAgent humanMock1 = control.createMock(HumanAgent.class);
        humans.add(humanMock1);
        Job job1 = control.createMock(Job.class);
        expect(humanMock1.getJob()).andReturn(job1).anyTimes();
        HumanAgent humanMock2 = control.createMock(HumanAgent.class);
        humans.add(humanMock2);
        Job job2 = control.createMock(Job.class);
        expect(humanMock2.getJob()).andReturn(job2).anyTimes();
        HumanAgent humanMock3 = control.createMock(HumanAgent.class);
        humans.add(humanMock3);
        expect(humanMock3.getJob()).andReturn(null).anyTimes();
        HumanAgent humanMock4 = control.createMock(HumanAgent.class);
        humans.add(humanMock4);
        expect(humanMock4.getJob()).andReturn(null).anyTimes();
        return humans;
    }

    private List<Job> getJobs(IMocksControl control) {
        List<Job> jobs = new ArrayList<Job>();
        Job jobMock1 = control.createMock(Job.class);
        jobs.add(jobMock1);
        Job jobMock2 = control.createMock(Job.class);
        jobs.add(jobMock2);
        return jobs;
    }
}
