package com.urban.app.renderers;

import com.google.common.eventbus.Subscribe;
import com.urban.simengine.agents.HumanAgent;
import com.urban.simengine.managers.family.events.ChildMovedOutEvent;
import com.urban.simengine.managers.family.events.CoupleCreatedEvent;
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
    public void handleTimeTickEvent(TimeTickEvent ev) {
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
        System.out.println(dateFormat.format(ev.getDate().getTime()));
    }

    @Subscribe
    public void handleJobFoundEvent(JobFoundEvent ev) {
        System.out.println(ev.getHuman()+" now works at "+ev.getHuman().getJob().getWorkStructure());
    }

    @Subscribe
    public void handleCoupleCreatedEvent(CoupleCreatedEvent ev) {
        HumanAgent[] couple = ev.getFamily().getMembers().toArray(new HumanAgent[ev.getFamily().getMembers().size()]);
        System.out.println(couple[0]+" has formed a couple with "+couple[1]);
    }

    @Subscribe
    public void handleChildMovedOutEvent(ChildMovedOutEvent ev) {
        System.out.println(ev.getHuman()+" now lives on his own!");
    }
}
