package com.urban.app.loaders;

import com.urban.simengine.*;
import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.agents.HumanAgentImpl;
import com.urban.simengine.managers.family.FamilyManager;
import com.urban.simengine.managers.family.FamilyManagerImpl;
import com.urban.simengine.managers.family.childmovers.BasicChildMover;
import com.urban.simengine.managers.family.childmovers.ChildMover;
import com.urban.simengine.managers.family.couplematchers.BasicCoupleMatcher;
import com.urban.simengine.managers.family.couplematchers.CoupleMatcher;
import com.urban.simengine.managers.family.movers.BasicMover;
import com.urban.simengine.managers.family.movers.Mover;
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

import com.google.common.eventbus.EventBus;

import org.joda.time.DateTime;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class XmlLoader implements Loader {
    private File xmlFile;
    private Map<Integer, Job> jobs = new HashMap<Integer, Job>();
    private Map<Integer, HumanAgent> humans = new HashMap<Integer, HumanAgent>();
    private Map<Integer, ResidenceStructure> residences = new HashMap<Integer, ResidenceStructure>();

    private Set<WorkStructure> workplaces = new HashSet<WorkStructure>();
    private Set<Family> families = new HashSet<Family>();

    public XmlLoader(String xmlFilename) {
        this.xmlFile = new File(xmlFilename);
    }

    private Document parseXmlFile() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
        return docBuilder.parse(this.xmlFile);
    }

    private void loadResidences(Element docElem) throws XPathExpressionException {
        XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList nodes = (NodeList) xpath.evaluate("/model/structures/residences/residence", docElem, XPathConstants.NODESET);

        for (int i = 0; i < nodes.getLength(); i++ ) {
            Element residenceElem = (Element) nodes.item(i);
            Point position = new Point(new Integer(residenceElem.getAttribute("x")), new Integer(residenceElem.getAttribute("y")));
            Dimension dimension = new Dimension(new Integer(residenceElem.getAttribute("width")), new Integer(residenceElem.getAttribute("height")));
            ResidenceStructure residence = new ResidenceStructureImpl(position, dimension, new Integer(residenceElem.getAttribute("capacity")));
            this.residences.put(new Integer(residenceElem.getAttribute("id")), residence);
        }
    }

    private void loadWorkplacesAndJobs(Element docElem) throws XPathExpressionException {
        XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList workplaceNodes = (NodeList) xpath.evaluate("/model/structures/workplaces/workplace", docElem, XPathConstants.NODESET);

        for (int i = 0; i < workplaceNodes.getLength(); i++) {
            Element workplaceElem = (Element) workplaceNodes.item(i);

            Point position = new Point(new Integer(workplaceElem.getAttribute("x")), new Integer(workplaceElem.getAttribute("y")));
            Dimension dimension = new Dimension(new Integer(workplaceElem.getAttribute("width")), new Integer(workplaceElem.getAttribute("height")));
            Set<Job> jobs = new HashSet<Job>();
            WorkStructure workplace = new WorkStructureImpl(position, dimension, jobs);

            NodeList jobNodes = (NodeList) xpath.evaluate("jobs/job", workplaceElem, XPathConstants.NODESET);
            for (int j = 0; j < jobNodes.getLength(); j++) {
                Element jobElem = (Element) jobNodes.item(j);
                JobImpl job = new JobImpl(SkillLevel.valueOf(jobElem.getAttribute("skill")), workplace);
                jobs.add(job);
                this.jobs.put(new Integer(jobElem.getAttribute("id")), job);
            }

            workplaces.add(workplace);
        }
    }

    private void loadFamiliesAndHumans(Element docElem) throws XPathExpressionException {
        XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList familyNodes = (NodeList) xpath.evaluate("/model/families/family", docElem, XPathConstants.NODESET);

        for (int i = 0; i < familyNodes.getLength(); i++) {
            Element familyElem = (Element) familyNodes.item(i);

            Family family = new FamilyImpl();
            if (familyElem.hasAttribute("residence")) {
                int residenceId = new Integer(familyElem.getAttribute("residence"));
                family.setResidence(this.residences.get(residenceId));
                this.residences.get(residenceId).getFamilies().add(family);
            }

            NodeList humanNodes = (NodeList) xpath.evaluate("members/human", familyElem, XPathConstants.NODESET);
            Map<Integer, Set<Integer>> relationships = new HashMap<Integer, Set<Integer>>();
            for (int j = 0; j < humanNodes.getLength(); j++) {
                Element humanElem = (Element) humanNodes.item(j);
                HumanAgentImpl human = new HumanAgentImpl(
                        DateTime.parse(humanElem.getAttribute("dateofbirth")).toGregorianCalendar(),
                        Gender.valueOf(humanElem.getAttribute("gender")),
                        humanElem.getAttribute("firstname"),
                        humanElem.getAttribute("lastname"),
                        null,
                        null,
                        family,
                        SkillLevel.valueOf(humanElem.getAttribute("skill"))
                );

                if (humanElem.hasAttribute("job")) {
                    int jobId = new Integer(humanElem.getAttribute("job"));
                    human.setJob(this.jobs.get(jobId));
                    this.jobs.get(jobId).setHuman(human);
                }

                NodeList parentNodes = (NodeList) xpath.evaluate("parent", humanElem, XPathConstants.NODESET);
                if (parentNodes.getLength() > 0) {
                    Set<Integer> parentIds = new HashSet<Integer>();
                    for (int k = 0; k < parentNodes.getLength(); k++) {
                        Element parentElem = (Element) parentNodes.item(k);
                        parentIds.add(new Integer(parentElem.getAttribute("id")));
                    }
                    relationships.put(new Integer(humanElem.getAttribute("id")), parentIds);
                }

                family.getMembers().add(human);
                this.humans.put(new Integer(humanElem.getAttribute("id")), human);
            }

            for (Integer childId : relationships.keySet()) {
                HumanAgent child = this.humans.get(childId);
                for (Integer parentId : relationships.get(childId)) {
                    HumanAgent parent = this.humans.get(parentId);
                    parent.getChildren().add(child);
                    child.getParents().add(parent);
                }
            }

            families.add(family);
        }
    }

    static private <K, V> Set<V> convertMapToHashSet(Map<K, V> map) {
        Set<V> set = new HashSet<V>();
        for (V item : map.values()) {
            set.add(item);
        }
        return set;
    }

    public Model getModel() throws LoaderException {
        try {
            System.out.println("Loading model from XML file " + this.xmlFile.getCanonicalPath());
            Document doc = this.parseXmlFile();
            Element docElem = doc.getDocumentElement();

            docElem.normalize();

            Calendar startDate = DateTime.parse(docElem.getAttribute("startdate")).toGregorianCalendar();
            int tickLength = new Integer(docElem.getAttribute("ticklength"));
            int tickLengthUnit;
            if (docElem.getAttribute("tickunit").equalsIgnoreCase("YEAR")) {
                tickLengthUnit = Calendar.YEAR;
            } else if (docElem.getAttribute("tickunit").equalsIgnoreCase("MONTH")) {
                tickLengthUnit = Calendar.MONTH;
            } else if (docElem.getAttribute("tickunit").equalsIgnoreCase("DAY")) {
                tickLengthUnit = Calendar.DAY_OF_YEAR;
            } else {
                throw new Exception("unsupported tick length unit");
            }

            Calendar endDate = null;
            if (docElem.hasAttribute("enddate")) {
                endDate = DateTime.parse(docElem.getAttribute("enddate")).toGregorianCalendar();
            }

            this.loadResidences(docElem);
            this.loadWorkplacesAndJobs(docElem);
            this.loadFamiliesAndHumans(docElem);

            EventBus eventBus = new EventBus();
            TimeManager timeManager = new TimeManagerImpl(startDate, tickLengthUnit, tickLength, eventBus);

            JobFinder jobFinder = new BasicJobFinder();
            PopulationManager populationManager = new PopulationManagerImpl(jobFinder, eventBus);
            for (HumanAgent human : this.humans.values()) {
                populationManager.getHumans().add(human);
            }

            Mover mover = new BasicMover();
            CoupleMatcher coupleMatcher = new BasicCoupleMatcher();
            ChildMover childMover = new BasicChildMover();
            FamilyManager familyManager = new FamilyManagerImpl(eventBus, mover, coupleMatcher, childMover);
            for (Family family : this.families) {
                familyManager.getFamilies().add(family);
            }

            if (endDate != null) {
                return new TimeLimitModel(eventBus, timeManager, endDate, populationManager, familyManager, convertMapToHashSet(residences), workplaces);
            } else {
                throw new Exception("unsupported model type");
            }
        } catch (Exception ex) {
            throw new LoaderException("unable to load model", ex);
        }
    }
}
