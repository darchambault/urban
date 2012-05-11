package com.urban.simengine.managers.family.childmovers;

import com.urban.simengine.Family;
import com.urban.simengine.agents.HumanAgent;

import java.util.Calendar;
import java.util.Set;

public interface ChildMover {
    public Set<HumanAgent> moveOutAdultChildren(Set<Family> families, Calendar currentDate);
}
