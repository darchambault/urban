package com.urban.simengine.managers.family;

import com.urban.simengine.Family;

import java.util.Calendar;
import java.util.Set;

public interface FamilyManager {
    public Set<Family> getFamilies();

    public void processTick(Calendar currentDate);
}
