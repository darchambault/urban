package com.urban.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.easymock.EasyMock.*;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.legacy.PowerMockRunner;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertSame;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Application.class})
public class ApplicationTest {
    @Test public void testMain() {
        Application applicationMock = createMockBuilder(Application.class)
                .addMockedMethod("runScenarioA")
                .createMock();

        applicationMock.main(new String[] {});

        replay(applicationMock);

//        assertEquals(SkillLevel.EXPERT, job.getSkillLevel());
//        assertSame(workStructureMock, job.getWorkStructure());

        verify(applicationMock);
    }
}
