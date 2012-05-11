package com.urban.app.loaders;

import com.urban.simengine.*;
import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.agents.HumanAgentImpl;
import com.urban.simengine.structures.ResidenceStructure;
import com.urban.simengine.structures.ResidenceStructureImpl;
import com.urban.simengine.structures.WorkStructure;
import com.urban.simengine.structures.WorkStructureImpl;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import com.urban.simengine.models.TimeLimitModel;

import java.awt.*;
import java.util.*;
import java.util.List;

public class XmlLoaderIT {
    @Test public void testValidScenarioLoads() throws LoaderException {
        List<HumanAgent> humans = new ArrayList<HumanAgent>();
        List<Family> families = new ArrayList<Family>();
        List<ResidenceStructure> residences = new ArrayList<ResidenceStructure>();
        List<WorkStructure> workplaces = new ArrayList<WorkStructure>();
        List<Job> jobs = new ArrayList<Job>();

        this.createFamily1(humans, families);
        this.createFamily2(humans, families);
        this.createResidences(residences);
        this.createWorkplaces(workplaces, jobs);

        jobs.get(0).setHuman(humans.get(0));
        humans.get(0).setJob(jobs.get(0));
        families.get(0).setResidence(residences.get(0));
        residences.get(0).getFamilies().add(families.get(0));

        Loader loader = new XmlLoader("src/test/java/com/urban/app/loaders/model_valid.xml");
        TimeLimitModel model = (TimeLimitModel) loader.getModel();

        Calendar expectedStartDate = new GregorianCalendar(1950, 0, 1);
        Calendar expectedEndDate = new GregorianCalendar(1960, 0, 1);

        assertEquals(expectedStartDate, model.getTimeManager().getStartDate());
        assertEquals(expectedEndDate, model.getEndDate());

        assertThat(model.getResidences().size(), equalTo(residences.size()));
        Set<ResidenceStructure> expectedResidences = new HashSet<ResidenceStructure>(residences);
        for (ResidenceStructure residence : model.getResidences()) {
            boolean found = false;
            for (ResidenceStructure expectedResidence : residences) {
                if (!found && this.compareResidences(residence, expectedResidence)) {
                    expectedResidences.remove(expectedResidence);
                    found = true;
                }
            }
        }
        assertThat(expectedResidences.size(), equalTo(0));

        assertThat(model.getWorkplaces().size(), equalTo(workplaces.size()));
        Set<WorkStructure> expectedWorkplaces = new HashSet<WorkStructure>(workplaces);
        for (WorkStructure workplace : model.getWorkplaces()) {
            boolean found = false;
            for (WorkStructure expectedWorkplace : workplaces) {
                if (!found && this.compareWorkplaces(workplace, expectedWorkplace)) {
                    expectedWorkplaces.remove(expectedWorkplace);
                    found = true;
                }
            }
        }
        assertThat(expectedWorkplaces.size(), equalTo(0));

        assertThat(model.getFamilyManager().getFamilies().size(), equalTo(families.size()));
        Set<Family> expectedFamilies = new HashSet<Family>(families);
        for (Family family : model.getFamilyManager().getFamilies()) {
            boolean found = false;
            for (Family expectedFamily : families) {
                if (!found && this.compareHumanSets(family.getMembers(), expectedFamily.getMembers())) {
                    expectedFamilies.remove(expectedFamily);
                    found = true;
                }
            }
        }
        assertThat(expectedFamilies.size(), equalTo(0));

        Set<HumanAgent> expectedHumans = new HashSet<HumanAgent>(humans);
        for (HumanAgent human : model.getPopulationManager().getHumans()) {
            boolean found = false;
            for (HumanAgent expectedHuman : humans) {
                if (!found && this.compareHumans(human, expectedHuman)) {
                    expectedHumans.remove(expectedHuman);
                    found = true;
                }
            }
        }
        assertThat(expectedHumans.size(), equalTo(0));
    }

    private void createFamily1(List<HumanAgent> humans, List<Family> families) {
        Family family1 = new FamilyImpl();
        Calendar humanDateOfBirth1 = new GregorianCalendar(1910, 0, 1);
        HumanAgent human1 = new HumanAgentImpl(humanDateOfBirth1, Gender.MALE, "James", "Smith", null, null, family1, SkillLevel.BASIC);
        family1.getMembers().add(human1);
        humans.add(human1);
        families.add(family1);

        Calendar humanDateOfBirth2 = new GregorianCalendar(1910, 0, 1);
        HumanAgent human2 = new HumanAgentImpl(humanDateOfBirth2, Gender.FEMALE, "Jill", "Smith", null, null, family1, SkillLevel.BASIC);
        family1.getMembers().add(human2);
        humans.add(human2);

        Calendar humanDateOfBirth3 = new GregorianCalendar(1933, 0, 1);
        HumanAgent human3 = new HumanAgentImpl(humanDateOfBirth3, Gender.FEMALE, "Jennifer", "Smith", null, null, family1, SkillLevel.BASIC);
        family1.getMembers().add(human3);
        humans.add(human3);

        human1.getChildren().add(human3);
        human2.getChildren().add(human3);
        human3.getParents().add(human1);
        human3.getParents().add(human2);
    }

    private void createFamily2(List<HumanAgent> humans, List<Family> families) {
        Family family1 = new FamilyImpl();
        Calendar humanDateOfBirth1 = new GregorianCalendar(1910, 0, 1);
        HumanAgent human1 = new HumanAgentImpl(humanDateOfBirth1, Gender.MALE, "John", "Doe", null, null, family1, SkillLevel.EXPERT);
        family1.getMembers().add(human1);
        humans.add(human1);
        families.add(family1);

        Calendar humanDateOfBirth2 = new GregorianCalendar(1910, 0, 1);
        HumanAgent human2 = new HumanAgentImpl(humanDateOfBirth2, Gender.FEMALE, "Joan", "Doe", null, null, family1, SkillLevel.INTERMEDIATE);
        family1.getMembers().add(human2);
        humans.add(human2);

        Calendar humanDateOfBirth3 = new GregorianCalendar(1935, 0, 1);
        HumanAgent human3 = new HumanAgentImpl(humanDateOfBirth3, Gender.MALE, "Jimmy", "Doe", null, null, family1, SkillLevel.NONE);
        family1.getMembers().add(human3);
        humans.add(human3);

        human1.getChildren().add(human3);
        human2.getChildren().add(human3);
        human3.getParents().add(human1);
        human3.getParents().add(human2);
    }

    private boolean compareHumans(HumanAgent human1, HumanAgent human2) {
        boolean isSame = true;

        isSame &= human1.getFirstName().equals(human2.getFirstName());
        isSame &= human1.getLastName().equals(human2.getLastName());
        isSame &= human1.getGender() == human2.getGender();
        isSame &= human1.getDateOfBirth().compareTo(human2.getDateOfBirth()) == 0;
        isSame &= human1.getSkillLevel() == human2.getSkillLevel();

        isSame &= this.compareHumanSets(human1.getParents(), human2.getParents());
        isSame &= this.compareHumanSets(human1.getChildren(), human2.getChildren());

        isSame &= this.compareHumanSets(human1.getFamily().getMembers(), human2.getFamily().getMembers());

        isSame &= (human1.getJob() == null && human2.getJob() == null)
                || (human1.getJob() != null && human2.getJob() != null && this.compareJobs(human1.getJob(), human2.getJob()));

        return isSame;
    }

    private boolean compareHumanSets(Set<HumanAgent> set1, Set<HumanAgent> set2) {
        Set<String> expectedParentNames = new HashSet<String>();
        if (set1.size() != set2.size()) {
            return false;
        }
        for (HumanAgent human : set1) {
            expectedParentNames.add(human.getFirstName()+" "+human.getLastName());
        }
        for (HumanAgent human : set2) {
            String humanName = human.getFirstName()+" "+human.getLastName();
            if (expectedParentNames.contains(humanName)) {
                expectedParentNames.remove(humanName);
            }
        }
        return expectedParentNames.size() == 0;
    }

    private void createResidences(List<ResidenceStructure> residences) {
        residences.add(new ResidenceStructureImpl(new Point(1, 1), new Dimension(2, 2), 2));
        residences.add(new ResidenceStructureImpl(new Point(3, 1), new Dimension(2, 2), 3));
    }

    private boolean compareResidences(ResidenceStructure residence1, ResidenceStructure residence2) {
        boolean isSame = true;

        isSame &= residence1.getMaximumFamilies() == residence2.getMaximumFamilies();
        isSame &= residence1.getPosition().equals(residence2.getPosition());
        isSame &= residence1.getDimension().equals(residence2.getDimension());

        return isSame;
    }

    private void createWorkplaces(List<WorkStructure> workplaces, List<Job> jobs) {
        Set<Job> jobs1 = new HashSet<Job>();
        WorkStructure workplace1 = new WorkStructureImpl(new Point(5, 1), new Dimension(2, 2), jobs1);
        JobImpl job1 = new JobImpl(SkillLevel.BASIC, workplace1);
        jobs1.add(job1);
        jobs.add(job1);
        workplaces.add(workplace1);

        Set<Job> jobs2 = new HashSet<Job>();
        WorkStructure workplace2 = new WorkStructureImpl(new Point(7, 1), new Dimension(2, 2), jobs2);
        JobImpl job2 = new JobImpl(SkillLevel.INTERMEDIATE, workplace2);
        jobs2.add(job2);
        jobs.add(job2);
        JobImpl job3 = new JobImpl(SkillLevel.EXPERT, workplace2);
        jobs2.add(job3);
        jobs.add(job3);
        workplaces.add(workplace2);
    }

    private boolean compareWorkplaces(WorkStructure workplace1, WorkStructure workplace2) {
        boolean isSame = true;

        isSame &= workplace1.getPosition().equals(workplace2.getPosition());
        isSame &= workplace1.getDimension().equals(workplace2.getDimension());

        isSame &= workplace1.getJobs().size() == workplace2.getJobs().size();

        isSame &= this.compareJobSets(workplace1.getJobs(), workplace2.getJobs());

        return isSame;
    }

    private boolean compareJobSets(Set<Job> set1, Set<Job> set2) {
        if (set1.size() != set2.size()) {
            return false;
        }
        Set<Job> expectedJobs = new HashSet<Job>(set1);
        for (Job job : set2) {
            boolean found = false;
            for (Job expectedJob : set1) {
                if (!found && this.compareJobs(job, expectedJob)) {
                    expectedJobs.remove(expectedJob);
                    found = true;
                }
            }
        }
        return expectedJobs.size() == 0;
    }

    private boolean compareJobs(Job job1, Job job2) {
        boolean isSame = true;

        isSame &= job1.getSkillLevel() == job2.getSkillLevel();
        isSame &= (job1.getHuman() == null && job2.getHuman() == null)
                || (job1.getHuman() != null && job2.getHuman() != null && job1.getHuman().getFirstName().equals(job2.getHuman().getFirstName()) && job1.getHuman().getLastName().equals(job2.getHuman().getLastName()));

        return isSame;
    }


}
