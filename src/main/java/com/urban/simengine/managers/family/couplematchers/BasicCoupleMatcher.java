package com.urban.simengine.managers.family.couplematchers;

import com.urban.simengine.Family;
import com.urban.simengine.Gender;
import com.urban.simengine.agents.HumanAgent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BasicCoupleMatcher implements CoupleMatcher {
    public Set<Family> createCouples(Set<Family> families) {
        Set<Family> newFamilies = new HashSet<Family>();

        List<HumanAgent> singleMales = new ArrayList<HumanAgent>();
        List<HumanAgent> singleFemales = new ArrayList<HumanAgent>();
        for (Family family : families) {
            if (family.getMembers().size() == 1) {
                for (HumanAgent human : family.getMembers()) {
                    if (human.getGender() == Gender.MALE) {
                        singleMales.add(human);
                    } else {
                        singleFemales.add(human);
                    }
                }
            }
        }

        while (singleMales.size() > 0 && singleFemales.size() > 0) {
            HumanAgent male = this.retrieveRandomHumanFromSet(singleMales);
            HumanAgent female = this.retrieveRandomHumanFromSet(singleFemales);

            newFamilies.add(this.mergeFamilies(male, female));
        }

        return newFamilies;
    }

    private HumanAgent retrieveRandomHumanFromSet(List<HumanAgent> humans) {
        int randomIndex = (int)Math.round(Math.random() * (humans.size() - 1));
        HumanAgent human = humans.get(randomIndex);
        humans.remove(randomIndex);
        return human;
    }

    private Family mergeFamilies(HumanAgent human1, HumanAgent human2) {
        Family newFamily = human1.getFamily();
        Family oldFamily = human2.getFamily();

//        oldFamily.getMembers().remove(human2);
        newFamily.getMembers().add(human2);
        human2.setFamily(newFamily);

        if (oldFamily.getResidence() != null) {
            oldFamily.getResidence().getFamilies().remove(oldFamily);
            oldFamily.setResidence(null);
        }

        return newFamily;
    }
}
