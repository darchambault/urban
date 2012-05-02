package com.urban.simengine;

import com.urban.simengine.agents.HumanAgent;

import java.util.Set;

public class JobFinder {
    static public Job findJob(HumanAgent human, Set<Job> jobs) throws NoJobFoundException {
        for (Job job : jobs) {
            if (job.getHuman() == null && human.getSkillLevel().compareTo(job.getSkillLevel()) >= 0) {
                job.setHuman(human);
                human.setJob(job);
                return job;
            }
        }
        throw new NoJobFoundException();
    }
}
