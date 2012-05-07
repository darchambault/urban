package com.urban.simengine;

import com.urban.simengine.models.Model;

public class ModelRunner {
    private Model model;

    public ModelRunner(Model model) {
        this.model = model;
    }

    public void run() {
        while (!model.isComplete()) {
            model.processTick();
            model.getTimeManager().tick();
        }
    }
}
