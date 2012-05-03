package com.urban.simengine.structures;

import com.urban.simengine.Job;

import java.awt.*;
import java.util.Set;

public class WorkStructureImpl extends BasicStructureImpl implements WorkStructure {
    private Set<Job> jobs;

    public WorkStructureImpl(Point position, Dimension dimension, Set<Job> jobs) {
        super(position, dimension);
        this.jobs = jobs;
    }

    public Set<Job> getJobs() {
        return this.jobs;
    }

    public boolean isFullyStaffed() {
        for (Job job : this.getJobs()) {
            if (job.getHuman() == null) {
                return false;
            }
        }
        return true;
    }
}
