package com.urban;

import com.urban.simengine.Job;
import com.urban.simengine.JobImpl;
import com.urban.simengine.SkillLevel;
import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.agents.HumanAgentImpl;
import com.urban.simengine.managers.population.PopulationManager;
import com.urban.simengine.managers.population.PopulationManagerImpl;
import com.urban.simengine.managers.population.jobfinders.BasicJobFinder;
import com.urban.simengine.managers.population.jobfinders.JobFinder;
import com.urban.simengine.managers.time.TimeManager;
import com.urban.simengine.managers.time.TimeManagerImpl;
import com.urban.simengine.models.Model;
import com.urban.simengine.models.TimeLimitModel;
import com.urban.simengine.structures.ResidenceStructure;
import com.urban.simengine.structures.ResidenceStructureImpl;
import com.urban.simengine.structures.WorkStructure;
import com.urban.simengine.structures.WorkStructureImpl;

import java.awt.Point;
import java.awt.Dimension;
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
        humans.add(new HumanAgentImpl((GregorianCalendar) startDate.clone(), SkillLevel.BASIC));
        humans.add(new HumanAgentImpl((GregorianCalendar) startDate.clone(), SkillLevel.BASIC));
        humans.add(new HumanAgentImpl((GregorianCalendar) startDate.clone(), SkillLevel.BASIC));

        Set<ResidenceStructure> residences = new HashSet<ResidenceStructure>();
        residences.add(new ResidenceStructureImpl(new Point(1, 1), new Dimension(2, 2), 2));
        residences.add(new ResidenceStructureImpl(new Point(3, 1), new Dimension(2, 2), 3));

        Set<WorkStructure> workplaces = new HashSet<WorkStructure>();

        Set<Job> jobs1 = new HashSet<Job>();
        WorkStructure workplace1 = new WorkStructureImpl(new Point(5, 1), new Dimension(2, 2), jobs1);
        jobs1.add(new JobImpl(SkillLevel.BASIC, workplace1));
        workplaces.add(workplace1);

        Set<Job> jobs2 = new HashSet<Job>();
        WorkStructure workplace2 = new WorkStructureImpl(new Point(5, 1), new Dimension(2, 2), jobs2);
        jobs2.add(new JobImpl(SkillLevel.EXPERT, workplace2));
        jobs2.add(new JobImpl(SkillLevel.BASIC, workplace2));
        workplaces.add(workplace2);

        JobFinder jobFinder = new BasicJobFinder();

        PopulationManager populationManager = new PopulationManagerImpl(jobFinder, humans);
        TimeManager timeManager = new TimeManagerImpl(startDate, Calendar.DAY_OF_MONTH, 1);

        Model model = new TimeLimitModel(timeManager, endDate, populationManager, residences, workplaces);

        Application.runModel(model);
    }

    private static void runModel(Model model) {
        System.out.println("Starting model!");

        while (!model.isComplete()) {
            DateFormat dateFormat = DateFormat.getDateInstance();
            System.out.println(dateFormat.format(model.getTimeManager().getCurrentDate().getTime()));
            model.processTick();
            model.getTimeManager().tick();
        }
    }
}
