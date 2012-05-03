package com.urban.simengine;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.structures.WorkStructure;

public class JobImplTest  {
    @Test public void testConstructorUnfilled() {
        WorkStructure workStructureMock = createMock(WorkStructure.class);

        replay(workStructureMock);

        Job job = new JobImpl(SkillLevel.EXPERT, workStructureMock);

        assertEquals(SkillLevel.EXPERT, job.getSkillLevel());
        assertSame(workStructureMock, job.getWorkStructure());

        verify(workStructureMock);
    }

    @Test public void testConstructorFilled() {
        WorkStructure workStructureMock = createMock(WorkStructure.class);
        HumanAgent humanMock = createMock(HumanAgent.class);

        replay(workStructureMock, humanMock);

        Job job = new JobImpl(SkillLevel.EXPERT, workStructureMock, humanMock);

        assertEquals(SkillLevel.EXPERT, job.getSkillLevel());
        assertSame(workStructureMock, job.getWorkStructure());
        assertSame(humanMock, job.getHuman());

        verify(workStructureMock, humanMock);
    }

    @Test public void testSetWorker() {
        WorkStructure workStructureMock = createMock(WorkStructure.class);
        HumanAgent humanMock = createMock(HumanAgent.class);

        replay(workStructureMock, humanMock);

        Job job = new JobImpl(SkillLevel.EXPERT, workStructureMock);
        job.setHuman(humanMock);

        assertSame(humanMock, job.getHuman());

        verify(workStructureMock, humanMock);
    }
}
