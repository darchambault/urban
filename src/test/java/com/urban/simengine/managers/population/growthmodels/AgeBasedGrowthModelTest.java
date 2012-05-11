package com.urban.simengine.managers.population.growthmodels;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;
import org.easymock.IMocksControl;

import com.urban.simengine.managers.time.TimeManager;
import com.urban.simengine.agents.HumanAgent;

import java.util.Set;
import java.util.HashSet;


public class AgeBasedGrowthModelTest {
    @Test public void testPerformGrowth() {
        IMocksControl control = createControl();

        Set<HumanAgent> humans = new HashSet<HumanAgent>();
        HumanAgent human = control.createMock(HumanAgent.class);
        humans.add(human);

        TimeManager timeManagerMock = control.createMock(TimeManager.class);

        control.replay();

        GrowthModel growthModel = new AgeBasedGrowthModel(timeManagerMock);
        Set<HumanAgent> newHumans = growthModel.performGrowth(humans, 5);

        assertEquals(0, newHumans.size());

        control.verify();
    }
}
