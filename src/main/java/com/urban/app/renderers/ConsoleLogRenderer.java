package com.urban.app.renderers;

import com.google.common.eventbus.Subscribe;
import com.urban.simengine.managers.population.events.JobFoundEvent;
import com.urban.simengine.managers.time.events.TimeTickEvent;
import com.urban.simengine.models.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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

    @Subscribe
    public void handleTimeTickEvent(TimeTickEvent ev) {
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
        System.out.println(dateFormat.format(ev.getDate().getTime()));
    }
}
