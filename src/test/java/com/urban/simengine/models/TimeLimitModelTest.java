package com.urban.simengine.models;

import com.urban.simengine.Job;
import com.urban.simengine.PopulationManager;
import com.urban.simengine.SkillLevel;
import com.urban.simengine.TimeManager;
import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.structures.ResidenceStructure;
import com.urban.simengine.structures.WorkStructure;
import junit.framework.TestCase;

import java.awt.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

public class TimeLimitModelTest extends TestCase {
    public void testConstructor() {
        GregorianCalendar startDate = new GregorianCalendar(1950, 1, 1);
        GregorianCalendar endDate = new GregorianCalendar(1950, 1, 7);

        Set<HumanAgent> humans = new HashSet<HumanAgent>();
        humans.add(new HumanAgent((GregorianCalendar) startDate.clone(), SkillLevel.BASIC));
        humans.add(new HumanAgent((GregorianCalendar) startDate.clone(), SkillLevel.BASIC));
        humans.add(new HumanAgent((GregorianCalendar) startDate.clone(), SkillLevel.BASIC));

        Set<ResidenceStructure> residences = new HashSet<ResidenceStructure>();
        residences.add(new ResidenceStructure(new Point(1, 1), new Dimension(2, 2), 2));
        residences.add(new ResidenceStructure(new Point(3, 1), new Dimension(2, 2), 3));

        Set<WorkStructure> workplaces = new HashSet<WorkStructure>();

        Set<Job> jobs1 = new HashSet<Job>();
        WorkStructure workplace1 = new WorkStructure(new Point(5, 1), new Dimension(2, 2), jobs1);
        jobs1.add(new Job(SkillLevel.BASIC, workplace1));
        workplaces.add(workplace1);

        Set<Job> jobs2 = new HashSet<Job>();
        WorkStructure workplace2 = new WorkStructure(new Point(5, 1), new Dimension(2, 2), jobs2);
        jobs2.add(new Job(SkillLevel.EXPERT, workplace2));
        jobs2.add(new Job(SkillLevel.BASIC, workplace2));
        workplaces.add(workplace2);

        PopulationManager populationManager = new PopulationManager(humans);
        TimeManager timeManager = new TimeManager(startDate, Calendar.DAY_OF_MONTH, 1);

        TimeLimitModel model = new TimeLimitModel(timeManager, endDate, populationManager, residences, workplaces);

        assertEquals(startDate, model.getTimeManager().getStartDate());
        assertEquals(startDate, model.getTimeManager().getCurrentDate());
        assertEquals(endDate, model.getEndDate());
        assertEquals(humans, model.getPopulationManager().getAllHumans());
    }

    public void testIsComplete() {
        GregorianCalendar startDate = new GregorianCalendar(1950, 1, 1);
        GregorianCalendar endDate = new GregorianCalendar(1950, 1, 2);

        Set<HumanAgent> humans = new HashSet<HumanAgent>();
        Set<ResidenceStructure> residences = new HashSet<ResidenceStructure>();
        Set<WorkStructure> workplaces = new HashSet<WorkStructure>();

        PopulationManager populationManager = new PopulationManager(humans);
        TimeManager timeManager = new TimeManager(startDate, Calendar.DAY_OF_MONTH, 1);

        AbstractModel model = new TimeLimitModel(timeManager, endDate, populationManager, residences, workplaces);

        assertFalse(model.isComplete());
        model.tick();
        assertTrue(model.isComplete());
    }
}
