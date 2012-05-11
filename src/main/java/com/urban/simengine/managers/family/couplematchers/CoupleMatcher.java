package com.urban.simengine.managers.family.couplematchers;

import com.urban.simengine.Family;

import java.util.Set;

public interface CoupleMatcher {
    public Set<Family> createCouples(Set<Family> families);
}
