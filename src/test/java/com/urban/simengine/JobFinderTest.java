package com.urban.simengine;

import junit.framework.TestCase;

import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.structures.WorkStructure;

import java.awt.*;
import java.util.*;

public class JobFinderTest extends TestCase {
    public void testFindJobOfEqualSkillLevel() {
        Set<Job> jobs = new HashSet<Job>();
        Job job1 = this.getJobFilled(SkillLevel.EXPERT);
        jobs.add(job1);
        Job job2 = this.getJobVacant(SkillLevel.INTERMEDIATE);
        jobs.add(job2);

        HumanAgent human = this.getUnemployedWorker(SkillLevel.INTERMEDIATE);

        try {
            Job foundJob = JobFinder.findJob(human, jobs);
            assertEquals(foundJob, job2);
        } catch (NoJobFoundException exc) {
            fail("should not throw NoJobFoundException");
        }
    }

    public void testFindJobOfLesserSkillLevel() {
        Set<Job> jobs = new HashSet<Job>();
        Job job1 = this.getJobFilled(SkillLevel.EXPERT);
        jobs.add(job1);
        Job job2 = this.getJobVacant(SkillLevel.BASIC);
        jobs.add(job2);

        HumanAgent human = this.getUnemployedWorker(SkillLevel.INTERMEDIATE);

        try {
            Job foundJob = JobFinder.findJob(human, jobs);
            assertEquals(foundJob, job2);
        } catch (NoJobFoundException exc) {
            fail("should not throw NoJobFoundException");
        }
    }

    public void testNoJobFoundWhenJobsHigherSkillLevel() {
        Set<Job> jobs = new HashSet<Job>();
        Job job1 = this.getJobFilled(SkillLevel.EXPERT);
        jobs.add(job1);
        Job job2 = this.getJobVacant(SkillLevel.ADVANCED);
        jobs.add(job2);

        HumanAgent human = this.getUnemployedWorker(SkillLevel.INTERMEDIATE);

        try {
            JobFinder.findJob(human, jobs);
            fail("should not find job");
        } catch (NoJobFoundException exc) {

        }
    }

    public void testNoJobFoundWhenNoJobAvailable() {
        Set<Job> jobs = new HashSet<Job>();
        Job job1 = this.getJobFilled(SkillLevel.EXPERT);
        jobs.add(job1);
        Job job2 = this.getJobFilled(SkillLevel.BASIC);
        jobs.add(job2);

        HumanAgent human = this.getUnemployedWorker(SkillLevel.INTERMEDIATE);

        try {
            JobFinder.findJob(human, jobs);
            fail("should not find job");
        } catch (NoJobFoundException exc) {

        }
    }

    private Job getJobFilled(SkillLevel skillLevel) {
        Set<Job> jobs = new HashSet<Job>();
        WorkStructure workplace = new WorkStructure(new Point(1, 2), new Dimension(3, 4), jobs);

        Job job = new Job(skillLevel, workplace);
        HumanAgent human = this.getUnemployedWorker(skillLevel);
        human.setJob(job);
        job.setHuman(human);

        jobs.add(job);

        return job;
    }

    private Job getJobVacant(SkillLevel skillLevel) {
        Set<Job> jobs = new HashSet<Job>();
        WorkStructure workplace = new WorkStructure(new Point(1, 2), new Dimension(3, 4), jobs);

        Job job = new Job(skillLevel, workplace);

        jobs.add(job);

        return job;
    }

    private HumanAgent getUnemployedWorker(SkillLevel skillLevel) {
        return new HumanAgent(new GregorianCalendar(2000, 12, 25), skillLevel);
    }
}
