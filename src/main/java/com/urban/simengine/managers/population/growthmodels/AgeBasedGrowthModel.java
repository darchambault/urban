package com.urban.simengine.managers.population.growthmodels;

import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.managers.time.TimeManager;
import org.joda.time.DateTime;
import org.joda.time.Years;

import java.util.*;

public class AgeBasedGrowthModel implements GrowthModel {
    int[][] ratios = {
            {15, 19, 123},
            {20, 24, 281},
            {25, 29, 277},
            {30, 34, 202},
            {35, 39, 94},
            {40, 44, 20},
            {45, 54, 1},
    };

    private TimeManager timeManager;

    public AgeBasedGrowthModel(TimeManager timeManager) {
        this.timeManager = timeManager;
    }

    public AgeBasedGrowthModel(TimeManager timeManager, int[][] ratios) {
        this.timeManager = timeManager;
        this.ratios = ratios;
    }

    public Set<HumanAgent> performGrowth(Set<HumanAgent> humans, int expectedBirths) {
        HashSet<HumanAgent> newHumans = new HashSet<HumanAgent>();

        int[] compiledRatios = new int[ratios.length];
        int sum = 0;
        for (int i = 0; i < ratios.length; i++) {
            compiledRatios[i] = ratios[i][2] + sum;
            sum = compiledRatios[i];
        }

        int rand = (int)Math.round(Math.random() * sum);

        int foundIndex;
        for (foundIndex = 0; foundIndex < compiledRatios.length && rand < compiledRatios[foundIndex]; foundIndex++) {
        }

        Set<HumanAgent> humansByAgeBracket = this.getHumansByAgeBracket(humans, ratios[foundIndex][0], ratios[foundIndex][1]);

        //TODO: what to do next?

        return newHumans;
    }

    private Set<HumanAgent> getHumansByAgeBracket(Set<HumanAgent> humans, int minAge, int maxAge) {
        HashSet<HumanAgent> foundHumans = new HashSet<HumanAgent>();

        for (HumanAgent human : humans) {
            Years age = Years.yearsBetween(new DateTime(human.getDateOfBirth().getTime()), new DateTime(timeManager.getCurrentDate().getTime()));
            if (age.isGreaterThan(Years.years(minAge)) && age.isLessThan(Years.years(maxAge+1))) {
                foundHumans.add(human);
            }
        }

        return foundHumans;
    }
}
