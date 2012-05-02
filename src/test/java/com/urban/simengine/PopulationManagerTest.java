package com.urban.simengine;

import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.structures.ResidenceStructure;

import com.urban.simengine.structures.WorkStructure;
import junit.framework.TestCase;

import java.awt.*;
import java.util.*;

public class PopulationManagerTest extends TestCase {
    public void testConstructorWithNoParams() {
        PopulationManager manager = new PopulationManager();

        assertEquals(0, manager.getAllHumans().size());
    }

    public void testConstructorWithAllHumans() {
        Set<HumanAgent> allHumans = new HashSet<HumanAgent>();
        HumanAgent human1 = this.getHuman();
        HumanAgent human2 = this.getHuman();
        allHumans.add(human1);
        allHumans.add(human2);

        PopulationManager manager = new PopulationManager(allHumans);

        assertEquals(2, manager.getAllHumans().size());
        assertTrue(manager.getAllHumans().contains(human1));
        assertTrue(manager.getAllHumans().contains(human2));
    }

    public void testAddHuman() {
        PopulationManager manager = new PopulationManager();

        HumanAgent human = this.getHuman();

        manager.addHuman(human);

        assertEquals(1, manager.getAllHumans().size());
        assertTrue(manager.getAllHumans().contains(human));
    }

    public void testGetUnemployedHumans() {
        Set<HumanAgent> humans = new HashSet<HumanAgent>();
        HumanAgent human1 = this.getEmployedWorker();
        humans.add(human1);
        HumanAgent human2 = this.getEmployedWorker();
        humans.add(human2);
        HumanAgent human3 = this.getUnemployedWorker();
        humans.add(human3);
        HumanAgent human4 = this.getUnemployedWorker();
        humans.add(human4);

        PopulationManager manager = new PopulationManager(humans);

        Set<HumanAgent> unemployedHumans = manager.getUnemployedHumans();

        assertEquals(2, unemployedHumans.size());
        assertTrue(unemployedHumans.contains(human3));
        assertTrue(unemployedHumans.contains(human4));
    }

    public void testGetEmployedHumans() {
        Set<HumanAgent> humans = new HashSet<HumanAgent>();
        HumanAgent human1 = this.getEmployedWorker();
        humans.add(human1);
        HumanAgent human2 = this.getEmployedWorker();
        humans.add(human2);
        HumanAgent human3 = this.getUnemployedWorker();
        humans.add(human3);
        HumanAgent human4 = this.getUnemployedWorker();
        humans.add(human4);

        PopulationManager manager = new PopulationManager(humans);

        Set<HumanAgent> employedHumans = manager.getEmployedHumans();

        assertEquals(2, employedHumans.size());
        assertTrue(employedHumans.contains(human1));
        assertTrue(employedHumans.contains(human2));
    }

    public void testProcessTick() {
        Set<HumanAgent> humans = new HashSet<HumanAgent>();
        HumanAgent human1 = this.getEmployedWorker();
        humans.add(human1);
        HumanAgent human2 = this.getEmployedWorker();
        humans.add(human2);
        HumanAgent human3 = this.getUnemployedWorker();
        humans.add(human3);
        HumanAgent human4 = this.getUnemployedWorker();
        humans.add(human4);

        PopulationManager manager = new PopulationManager(humans);

        manager.processTick(new GregorianCalendar(2012, 1, 1));

        assertTrue(human1.getJob() != null);
        assertTrue(human2.getJob() != null);
        assertTrue(human3.getJob() != null);
        assertTrue(human4.getJob() != null);
    }

    private HumanAgent getHuman() {
        ResidenceStructure residence = new ResidenceStructure(new Point(1, 2), new Dimension(3, 4), 2);
        HumanAgent human = new HumanAgent(new GregorianCalendar(2000, 12, 25), SkillLevel.BASIC);
        residence.getResidents().add(human);
        return human;
    }

    private HumanAgent getUnemployedWorker() {
        ResidenceStructure residence = new ResidenceStructure(new Point(1, 2), new Dimension(3, 4), 2);
        HumanAgent human = new HumanAgent(new GregorianCalendar(2000, 12, 25), SkillLevel.BASIC);
        residence.getResidents().add(human);
        return human;
    }

    private HumanAgent getEmployedWorker() {
        HumanAgent human = this.getUnemployedWorker();
        WorkStructure workplace = new WorkStructure(new Point(1, 2), new Dimension(3, 4), new HashSet<Job>());
        Job job = new Job(SkillLevel.INTERMEDIATE, workplace, human);
        workplace.getJobs().add(job);
        human.setJob(job);
        return human;
    }
}
