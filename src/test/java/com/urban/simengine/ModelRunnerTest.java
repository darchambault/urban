package com.urban.simengine;

import org.junit.Test;
import static org.easymock.EasyMock.*;
import org.easymock.IMocksControl;

import com.urban.simengine.managers.time.TimeManager;
import com.urban.simengine.models.Model;

import java.util.GregorianCalendar;

public class ModelRunnerTest {
    @Test public void testRun() {
        IMocksControl control = createControl();

        Model modelMock = control.createMock(Model.class);
        TimeManager timeManagerMock = control.createMock(TimeManager.class);
        GregorianCalendar newDateMock = control.createMock(GregorianCalendar.class);

        expect(modelMock.isComplete()).andReturn(false).once();

        modelMock.processTick();
        expectLastCall().once();

        expect(modelMock.getTimeManager()).andReturn(timeManagerMock).once();

        expect(timeManagerMock.tick()).andReturn(newDateMock).once();

        expect(modelMock.isComplete()).andReturn(true).once();

        control.replay();

        ModelRunner.run(modelMock);

        control.verify();
    }
}
