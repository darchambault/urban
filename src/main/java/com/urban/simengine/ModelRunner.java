package com.urban.simengine;

import com.urban.simengine.models.Model;

public class ModelRunner {
    static public void run(Model model) {
        while (!model.isComplete()) {
            model.processTick();
            model.getTimeManager().tick();
        }
    }
}
