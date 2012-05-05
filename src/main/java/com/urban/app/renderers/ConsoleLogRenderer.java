package com.urban.app.renderers;

import com.google.common.eventbus.Subscribe;
import com.urban.simengine.managers.population.events.JobFoundEvent;
import com.urban.simengine.models.Model;

public class ConsoleLogRenderer implements Renderer {
    private Model model;

    public ConsoleLogRenderer(Model model) {
        this.model = model;
    }

    public void start() {
        this.model.getEventBus().register(this);
    }

    @Subscribe
    public void handleJobFoundEvent(JobFoundEvent ev) {
        System.out.println(ev.getHuman()+" now works at "+ev.getHuman().getJob().getWorkStructure());
    }
}
