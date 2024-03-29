package com.urban.simengine.managers.population.jobfinders;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;
import org.easymock.IMocksControl;

import com.urban.simengine.Job;
import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.SkillLevel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BasicJobFinderTest {
    @Test public void testFindJobOfEqualSkillLevel() {
        IMocksControl control = createControl();

        List<HumanAgent> unemployedHumans = new ArrayList<HumanAgent>();
        HumanAgent human = control.createMock(HumanAgent.class);
        unemployedHumans.add(human);

        List<Job> unfilledJobs = new ArrayList<Job>();
        Job job1 = control.createMock(Job.class);
        unfilledJobs.add(job1);

        expect(job1.getSkillLevel()).andReturn(SkillLevel.INTERMEDIATE).atLeastOnce();
        expect(job1.getHuman()).andReturn(null).atLeastOnce();

        expect(human.getJob()).andReturn(null).atLeastOnce();
        expect(human.getSkillLevel()).andReturn(SkillLevel.INTERMEDIATE).atLeastOnce();

        expect(human.setJob(anyObject(Job.class))).andReturn(human).once();
        expect(job1.setHuman(human)).andReturn(job1).once();

        control.replay();

        JobFinder jobFinder = new BasicJobFinder();
        Set<HumanAgent> humansWithNewJobs = jobFinder.findJobs(new HashSet<HumanAgent>(unemployedHumans), new HashSet<Job>(unfilledJobs));

        assertEquals(1, humansWithNewJobs.size());
        assertTrue(humansWithNewJobs.contains(human));

        control.verify();
    }

    @Test public void testFindJobOfLesserSkillLevel() {
        IMocksControl control = createControl();

        List<HumanAgent> unemployedHumans = new ArrayList<HumanAgent>();
        HumanAgent human = control.createMock(HumanAgent.class);
        unemployedHumans.add(human);

        List<Job> unfilledJobs = new ArrayList<Job>();
        Job job1 = control.createMock(Job.class);
        unfilledJobs.add(job1);

        expect(job1.getSkillLevel()).andReturn(SkillLevel.BASIC).atLeastOnce();
        expect(job1.getHuman()).andReturn(null).atLeastOnce();

        expect(human.getJob()).andReturn(null).atLeastOnce();
        expect(human.getSkillLevel()).andReturn(SkillLevel.INTERMEDIATE).atLeastOnce();

        expect(human.setJob(anyObject(Job.class))).andReturn(human).once();
        expect(job1.setHuman(human)).andReturn(job1).once();

        control.replay();

        JobFinder jobFinder = new BasicJobFinder();
        Set<HumanAgent> humansWithNewJobs = jobFinder.findJobs(new HashSet<HumanAgent>(unemployedHumans), new HashSet<Job>(unfilledJobs));

        assertEquals(1, humansWithNewJobs.size());
        assertTrue(humansWithNewJobs.contains(human));

        control.verify();
    }

    @Test public void testNoJobFoundWhenJobsHigherSkillLevel() {
        IMocksControl control = createControl();

        List<HumanAgent> unemployedHumans = new ArrayList<HumanAgent>();
        HumanAgent human = control.createMock(HumanAgent.class);
        unemployedHumans.add(human);

        List<Job> unfilledJobs = new ArrayList<Job>();
        Job job1 = control.createMock(Job.class);
        unfilledJobs.add(job1);

        expect(job1.getSkillLevel()).andReturn(SkillLevel.EXPERT).atLeastOnce();
        expect(job1.getHuman()).andReturn(null).atLeastOnce();

        expect(human.getJob()).andReturn(null).atLeastOnce();
        expect(human.getSkillLevel()).andReturn(SkillLevel.INTERMEDIATE).atLeastOnce();

        control.replay();

        JobFinder jobFinder = new BasicJobFinder();
        Set<HumanAgent> humansWithNewJobs = jobFinder.findJobs(new HashSet<HumanAgent>(unemployedHumans), new HashSet<Job>(unfilledJobs));

        assertEquals(0, humansWithNewJobs.size());

        control.verify();
    }

    @Test public void testNoJobFoundWhenNoJobAvailable() {
        IMocksControl control = createControl();

        List<HumanAgent> unemployedHumans = new ArrayList<HumanAgent>();
        HumanAgent human1 = control.createMock(HumanAgent.class);
        unemployedHumans.add(human1);

        HumanAgent human2 = control.createMock(HumanAgent.class);

        List<Job> unfilledJobs = new ArrayList<Job>();
        Job job1 = control.createMock(Job.class);
        unfilledJobs.add(job1);

        expect(job1.getSkillLevel()).andReturn(SkillLevel.BASIC).anyTimes();
        expect(job1.getHuman()).andReturn(human2).atLeastOnce();

        expect(human1.getJob()).andReturn(null).atLeastOnce();
        expect(human1.getSkillLevel()).andReturn(SkillLevel.INTERMEDIATE).anyTimes();

        control.replay();

        JobFinder jobFinder = new BasicJobFinder();
        Set<HumanAgent> humansWithNewJobs = jobFinder.findJobs(new HashSet<HumanAgent>(unemployedHumans), new HashSet<Job>(unfilledJobs));

        assertEquals(0, humansWithNewJobs.size());

        control.verify();
    }

    @Test public void testDoesNotTryToFindJobToEmployedHuman() {
        IMocksControl control = createControl();

        List<HumanAgent> unemployedHumans = new ArrayList<HumanAgent>();
        HumanAgent human1 = control.createMock(HumanAgent.class);
        unemployedHumans.add(human1);

        List<Job> unfilledJobs = new ArrayList<Job>();
        Job job1 = control.createMock(Job.class);
        unfilledJobs.add(job1);

        Job job2 = control.createMock(Job.class);

        expect(job1.getSkillLevel()).andReturn(SkillLevel.BASIC).anyTimes();
        expect(job1.getHuman()).andReturn(null).anyTimes();

        expect(human1.getJob()).andReturn(job2).atLeastOnce();
        expect(human1.getSkillLevel()).andReturn(SkillLevel.INTERMEDIATE).anyTimes();

        control.replay();

        JobFinder jobFinder = new BasicJobFinder();
        Set<HumanAgent> humansWithNewJobs = jobFinder.findJobs(new HashSet<HumanAgent>(unemployedHumans), new HashSet<Job>(unfilledJobs));

        assertEquals(0, humansWithNewJobs.size());

        control.verify();
    }
}
