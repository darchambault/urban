package com.urban;

import com.urban.simengine.*;
import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.models.AbstractModel;
import com.urban.simengine.models.TimeLimitModel;
import com.urban.simengine.structures.ResidenceStructure;
import com.urban.simengine.structures.WorkStructure;

import java.awt.*;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

public class Application {
    public static void main(String [] args) {
        Application.runScenarioA();
    }

    private static void runScenarioA() {
        System.out.println("Configuring scenario A ...");

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

        AbstractModel model = new TimeLimitModel(timeManager, endDate, populationManager, residences, workplaces);

        Application.runModel(model);
    }

    private static void runModel(AbstractModel model) {
        System.out.println("Starting model!");

        while (!model.isComplete()) {
            DateFormat dateFormat = DateFormat.getDateInstance();
            System.out.println(dateFormat.format(model.getTimeManager().getCurrentDate().getTime()));
            model.tick();
        }
    }
}
