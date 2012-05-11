package com.urban.simengine.agents;

import com.urban.simengine.Family;
import com.urban.simengine.Gender;
import com.urban.simengine.Job;
import com.urban.simengine.SkillLevel;
import org.joda.time.DateTime;
import org.joda.time.Years;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class HumanAgentImpl extends BasicAgentImpl implements HumanAgent {
    private Calendar dateOfBirth;
    private Gender gender;
    private String firstName;
    private String lastName;
    private Set<HumanAgent> parents = new HashSet<HumanAgent>();
    private Set<HumanAgent> children = new HashSet<HumanAgent>();
    private Family family;
    private SkillLevel skillLevel;
    private Job job;

    public HumanAgentImpl(Calendar dateOfBirth, Gender gender, String firstName, String lastName, Set<HumanAgent> parents, Set<HumanAgent> children, Family family, SkillLevel skillLevel) {
        this.dateOfBirth = dateOfBirth;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        if (parents != null) {
            for (HumanAgent parent : parents) {
                this.parents.add(parent);
            }
        }
        if (children != null) {
            for (HumanAgent child : children) {
                this.children.add(child);
            }
        }
        this.family = family;
        this.skillLevel = skillLevel;
    }

    public Calendar getDateOfBirth() {
        return this.dateOfBirth;
    }

    public Gender getGender() {
        return this.gender;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Set<HumanAgent> getParents() {
        return this.parents;
    }

    public Set<HumanAgent> getChildren() {
        return this.children;
    }

    public Family getFamily() {
        return this.family;
    }

    public HumanAgent setFamily(Family family) {
        this.family = family;
        return this;
    }

    public SkillLevel getSkillLevel() {
        return this.skillLevel;
    }

    public HumanAgent setSkillLevel(SkillLevel skillLevel) {
        this.skillLevel = skillLevel;
        return this;
    }

    public Job getJob() {
        return this.job;
    }

    public HumanAgent setJob(Job job) {
        this.job = job;
        return this;
    }

    public int getAge(Calendar currentDate) {
        Years age = Years.yearsBetween(this.getDateTimeForCalendar(this.getDateOfBirth()), this.getDateTimeForCalendar(currentDate));
        return age.getYears();
    }

    public String toString() {
        return this.getFirstName()+" "+this.getLastName()+" #"+this.hashCode();
    }

    private DateTime getDateTimeForCalendar(Calendar date) {
        return new DateTime(date.getTime());
    }
}
