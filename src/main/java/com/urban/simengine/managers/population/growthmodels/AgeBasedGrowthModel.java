package com.urban.simengine.managers.population.growthmodels;

import com.urban.simengine.Family;
import com.urban.simengine.Gender;
import com.urban.simengine.SkillLevel;
import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.agents.HumanAgentImpl;

import java.util.*;

public class AgeBasedGrowthModel implements GrowthModel {
    private int[][] ageDistribution = {
            {18, 19, 123},
            {20, 24, 281},
            {25, 29, 277},
            {30, 34, 202},
            {35, 39, 94},
            {40, 44, 20},
            {45, 54, 1},
    };

    private List<Calendar> birthPlan = new ArrayList<Calendar>();
    private int previousPlannedBirths;

    private int currentYear;

    private List<HumanAgent> females = new ArrayList<HumanAgent>();
    private Map<Integer, List<HumanAgent>> femalesByAgeBracket = new HashMap<Integer, List<HumanAgent>>();

    public Set<HumanAgent> performGrowth(Set<HumanAgent> humans, int yearlyBirthRate, Calendar currentDate, int tickLength, int tickLengthUnit) {
        int expectedBirthsForYear = (int) Math.ceil((float) humans.size() * yearlyBirthRate / 10000);
        int birthsToPlan = 0;
        if (this.currentYear != currentDate.get(Calendar.YEAR)) {
            this.currentYear = currentDate.get(Calendar.YEAR);
            this.previousPlannedBirths = expectedBirthsForYear;
            birthsToPlan = expectedBirthsForYear;
        } else if (expectedBirthsForYear > this.previousPlannedBirths) {
            this.previousPlannedBirths = expectedBirthsForYear;
            birthsToPlan = (expectedBirthsForYear - this.previousPlannedBirths);
        } else if (expectedBirthsForYear < this.previousPlannedBirths) {
            int birthsToRemove = (this.previousPlannedBirths - expectedBirthsForYear);
            if (birthsToRemove >= this.birthPlan.size()) {
                this.birthPlan.clear();
            } else {
                for (int i = 0; i < birthsToRemove; i++) {
                    this.birthPlan.remove(this.getRandomItem(this.birthPlan));
                }
            }
        }

        //TODO: find a way to distribute birthRate more evenly when there are very few females

        if (birthsToPlan > 0) {
            this.planBirthsForYear(birthsToPlan, currentDate);
        }

        HashSet<HumanAgent> newHumans = new HashSet<HumanAgent>();

        Calendar birthDate;
        Calendar tickEnd = (Calendar) currentDate.clone();
        tickEnd.add(tickLengthUnit, tickLength);

        this.extractElligibleFemales(humans, currentDate);
        this.prepareAgeBrackets(currentDate);

        while (!this.birthPlan.isEmpty() && (birthDate = this.birthPlan.get(0)) != null && birthDate.before(tickEnd)) {
            Family family = this.chooseFamily();
            if (family != null) {
                newHumans.add(this.doBirth(birthDate, family));
            }
            this.birthPlan.remove(0);
        }

        return newHumans;
    }

    private void extractElligibleFemales(Set<HumanAgent> humans, Calendar currentDate) {
        //TODO: implement "rest periods" between babies!
        int[][] ageDist = this.getAgeDistribution();
        for (HumanAgent human : humans) {
            Set<HumanAgent> parents = human.getFamily().getParents();
            if (human.getGender() == Gender.FEMALE && parents.contains(human) && parents.size() == 2) {
                int age = human.getAge(currentDate);
                if (age >= ageDist[0][0] && age <= ageDist[ageDist.length-1][1]) {
                    this.females.add(human);
                }
            }
        }
    }

    private void prepareAgeBrackets(Calendar currentDate) {
        for (int ageBracketIndex = 0; ageBracketIndex < this.getAgeDistribution().length; ageBracketIndex++) {
            this.femalesByAgeBracket.put(ageBracketIndex, new ArrayList<HumanAgent>());
        }
        for (HumanAgent human : this.females) {
            int ageBracketIndex = this.findAgeBracketIndexForHuman(human, currentDate);
            this.femalesByAgeBracket.get(ageBracketIndex).add(human);
        }
    }

    private int findAgeBracketIndexForHuman(HumanAgent human, Calendar currentDate) {
        int[][] ageDist = this.getAgeDistribution();
        int age = human.getAge(currentDate);
        if (age < ageDist[0][0] || age > ageDist[ageDist.length-1][1]) {
            return -1;
        }
        int foundIndex;
        for (foundIndex = 0; foundIndex < ageDist.length && age > ageDist[foundIndex][1]; foundIndex++) {
        }
        return foundIndex;
    }

    private <T> T getRandomItem(List<T> list) {
        if (list.isEmpty()) {
            return null;
        }
        int randomIdx = (int) Math.floor(Math.random() * (list.size() - 1));
        return list.get(randomIdx);
    }

    private Family chooseFamily() {
        List<int[]> ageDistribution = new ArrayList<int[]>(Arrays.asList(this.getAgeDistribution()));
        List<HumanAgent> humansByAgeBracket;

        do {
            int ageBracketIndex = this.getRandomIndexFromDistribution(ageDistribution.toArray(new int[][] {}), 2);
            humansByAgeBracket = this.getHumansByAgeBracket(ageDistribution.get(ageBracketIndex));
            ageDistribution.remove(ageBracketIndex);
        } while (humansByAgeBracket.isEmpty() && !ageDistribution.isEmpty());

        if (humansByAgeBracket.isEmpty()) {
            return null;
        }

        return this.getRandomItem(humansByAgeBracket).getFamily();
    }

    private HumanAgent doBirth(Calendar dateOfBirth, Family family) {
        Gender gender = (Math.random() >= 0.5 ? Gender.FEMALE : Gender.MALE);

        //TODO: generate random names
        String firstName = (gender == Gender.MALE ? "John" : "Joan");
        String lastName = "Doe";

        return new HumanAgentImpl(dateOfBirth, gender, firstName, lastName, family.getParents(), null, family, SkillLevel.NONE);
    }

    private void planBirthsForYear(int expectedBirths, Calendar currentDate) {
        this.birthPlan.clear();
        int daysInCurrentYear = (new GregorianCalendar(currentDate.get(Calendar.YEAR), 11, 31)).get(Calendar.DAY_OF_YEAR);
        int currentDayOfYear = currentDate.get(Calendar.DAY_OF_YEAR);
        int daysToPlanFor = (daysInCurrentYear - currentDayOfYear + 1);
        for (int i = 0; i < expectedBirths; i++) {
            int dayOfYear = (int) Math.floor(Math.random() * (daysToPlanFor - 1)) + currentDayOfYear + 1;
            Calendar birthDate = new GregorianCalendar(currentDate.get(Calendar.YEAR), 0, 1);
            birthDate.add(Calendar.DAY_OF_YEAR, (dayOfYear - 1));
            this.birthPlan.add(birthDate);
        }
        Collections.sort(this.birthPlan);
    }

    private List<HumanAgent> getHumansByAgeBracket(int[] ageBracket) {
        int[][] ageDistribution = this.getAgeDistribution();
        for (int ageBracketIndex = 0; ageBracketIndex < ageDistribution.length; ageBracketIndex++) {
            if (ageDistribution[ageBracketIndex] == ageBracket) {
                return this.femalesByAgeBracket.get(ageBracketIndex);
            }
        }
        return null;
    }

    private int getRandomIndexFromDistribution(int[][] distribution, int index) {
        int[] cumulativeDistribution = new int[distribution.length];
        int cumulativeSum = 0;
        for (int i = 0; i < distribution.length; i++) {
            cumulativeDistribution[i] = distribution[i][index] + cumulativeSum;
            cumulativeSum = cumulativeDistribution[i];
        }

        int rand = (int)Math.round(Math.random() * cumulativeSum);

        int foundIndex;
        for (foundIndex = 0; foundIndex < cumulativeDistribution.length && rand > cumulativeDistribution[foundIndex]; foundIndex++) {
        }

        return foundIndex;
    }

    private int[][] getAgeDistribution() {
        return this.ageDistribution;
    }
}
