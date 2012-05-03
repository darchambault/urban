package com.urban.simengine.structures;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;
import org.easymock.IMocksControl;

import com.urban.simengine.Job;
import com.urban.simengine.agents.HumanAgent;

import java.awt.*;
import java.util.*;

public class WorkStructureImplTest {
    @Test public void testConstructorWithoutWorkers() {
        IMocksControl control = createControl();

        Point pointMock = control.createMock(Point.class);
        Dimension dimensionMock = control.createMock(Dimension.class);

        Set<Job> jobs = new HashSet<Job>();
        Job positionMock1 = control.createMock(Job.class);
        Job positionMock2 = control.createMock(Job.class);
        jobs.add(positionMock1);
        jobs.add(positionMock2);

        control.replay();

        WorkStructure work = new WorkStructureImpl(pointMock, dimensionMock, jobs);

        assertSame(pointMock, work.getPosition());
        assertSame(dimensionMock, work.getDimension());
        assertEquals(2, work.getJobs().size());
        assertTrue(work.getJobs().contains(positionMock1));
        assertTrue(work.getJobs().contains(positionMock2));

        control.verify();
    }

    @Test public void testDetectsNotFullyStaffed() {
        IMocksControl control = createControl();

        Point pointMock = control.createMock(Point.class);
        Dimension dimensionMock = control.createMock(Dimension.class);

        Set<Job> jobs = new HashSet<Job>();
        Job positionMock1 = control.createMock(Job.class);
        Job positionMock2 = control.createMock(Job.class);
        jobs.add(positionMock1);
        jobs.add(positionMock2);

        expect(positionMock1.getHuman()).andReturn(null).anyTimes();
        expect(positionMock2.getHuman()).andReturn(null).anyTimes();

        control.replay();

        WorkStructure work = new WorkStructureImpl(pointMock, dimensionMock, jobs);

        assertFalse(work.isFullyStaffed());

        control.verify();
    }

    @Test public void testDetectsFullyStaffed() {
        IMocksControl control = createControl();

        Point pointMock = control.createMock(Point.class);
        Dimension dimensionMock = control.createMock(Dimension.class);

        Set<Job> jobs = new HashSet<Job>();
        Job positionMock1 = control.createMock(Job.class);
        Job positionMock2 = control.createMock(Job.class);
        jobs.add(positionMock1);
        jobs.add(positionMock2);

        HumanAgent human1 = control.createMock(HumanAgent.class);
        HumanAgent human2 = control.createMock(HumanAgent.class);

        expect(positionMock1.getHuman()).andReturn(human1).anyTimes();
        expect(positionMock2.getHuman()).andReturn(human2).anyTimes();

        control.replay();

        WorkStructure work = new WorkStructureImpl(pointMock, dimensionMock, jobs);

        assertTrue(work.isFullyStaffed());

        control.verify();
    }
}
