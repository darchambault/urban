package com.urban.simengine.structures;

import com.urban.simengine.Job;

import java.util.Set;

public interface WorkStructure extends BasicStructure {
    public Set<Job> getJobs();

    public boolean isFullyStaffed();
}
