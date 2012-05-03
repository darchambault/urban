package com.urban.simengine.managers.population.jobfinders;

import com.urban.simengine.Job;
import com.urban.simengine.agents.HumanAgent;

import java.util.Set;

public class BasicJobFinder implements JobFinder {
    public void findJobs(Set<HumanAgent> unemployedHumans, Set<Job> unfilledJobs) {
        for (HumanAgent human : unemployedHumans) {
            if (human.getJob() == null) {
                this.findJob(human, unfilledJobs);
            }
        }
    }

    private Job findJob(HumanAgent human, Set<Job> jobs) {
        for (Job job : jobs) {
            if (job.getHuman() == null && human.getSkillLevel().compareTo(job.getSkillLevel()) >= 0) {
                job.setHuman(human);
                human.setJob(job);
                return job;
            }
        }
        return null;
    }
}
