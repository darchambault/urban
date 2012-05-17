package com.urban.simengine.managers.population.growthmodels;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;
import org.easymock.IMocksControl;

import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.Family;
import com.urban.simengine.Gender;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.HashSet;


public class AgeBasedGrowthModelTest {
    @Test public void testPerformGrowth() {
        IMocksControl control = createControl();

        int populationSize = 200;
        int birthRatePerTenThousand = 1200;

        Set<HumanAgent> humans = new HashSet<HumanAgent>();
        for (int i = 0; i < (populationSize / 2); i++) {
            Family familyMock = control.createMock(Family.class);

            HumanAgent maleMock = control.createMock(HumanAgent.class);
            expect(maleMock.getGender()).andReturn(Gender.MALE).anyTimes();
            expect(maleMock.getFamily()).andReturn(familyMock).anyTimes();
            expect(maleMock.getParents()).andReturn(new HashSet<HumanAgent>()).anyTimes();
            expect(maleMock.getAge(anyObject(Calendar.class))).andReturn(22).anyTimes();
            humans.add(maleMock);

            HumanAgent femaleMock = control.createMock(HumanAgent.class);
            expect(femaleMock.getGender()).andReturn(Gender.FEMALE).anyTimes();
            expect(femaleMock.getFamily()).andReturn(familyMock).anyTimes();
            expect(femaleMock.getParents()).andReturn(new HashSet<HumanAgent>()).anyTimes();
            expect(femaleMock.getAge(anyObject(Calendar.class))).andReturn(22).anyTimes();
            humans.add(femaleMock);

            Set<HumanAgent> parents = new HashSet<HumanAgent>();
            parents.add(maleMock);
            parents.add(femaleMock);

            expect(familyMock.getParents()).andReturn(parents).anyTimes();
        }

        control.replay();

        GrowthModel growthModel = new AgeBasedGrowthModel();
        Set<HumanAgent> newHumans = new HashSet<HumanAgent>();
        GregorianCalendar currentDate = new GregorianCalendar(1950, 0, 1);
        for (int i = 0; i < 12; i++) {
            newHumans.addAll(growthModel.performGrowth(humans, birthRatePerTenThousand, currentDate, 1, Calendar.MONTH));
            currentDate.add(Calendar.MONTH, 1);
        }

        assertEquals(Math.round((float) birthRatePerTenThousand / 10000 * populationSize), newHumans.size());

        control.verify();
    }
}
