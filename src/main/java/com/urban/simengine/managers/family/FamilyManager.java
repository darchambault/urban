package com.urban.simengine.managers.family;

import com.urban.simengine.Family;
import com.urban.simengine.structures.ResidenceStructure;

import java.util.Calendar;
import java.util.Set;

public interface FamilyManager {
    public Set<Family> getFamilies();

    public Set<Family> getHomelessFamilies();

    public void processTick(Calendar currentDate, Set<ResidenceStructure> residencesWithVacancy);
}
