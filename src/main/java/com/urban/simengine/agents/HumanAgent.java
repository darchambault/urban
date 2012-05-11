package com.urban.simengine.agents;

import com.urban.simengine.Family;
import com.urban.simengine.Gender;
import com.urban.simengine.Job;
import com.urban.simengine.SkillLevel;

import java.util.Calendar;
import java.util.Set;

public interface HumanAgent extends BasicAgent {
    public Calendar getDateOfBirth();

    public int getAge(Calendar currentDate);

    public Gender getGender();

    public String getFirstName();

    public String getLastName();

    public Set<HumanAgent> getParents();

    public Set<HumanAgent> getChildren();

    public Family getFamily();

    public HumanAgent setFamily(Family family);

    public SkillLevel getSkillLevel();

    public HumanAgent setSkillLevel(SkillLevel skillLevel);

    public Job getJob();

    public HumanAgent setJob(Job job);
}
