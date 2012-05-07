package com.urban.app;

import com.urban.app.loaders.Loader;
import com.urban.app.loaders.ScenarioALoader;
import com.urban.app.renderers.ConsoleLogRenderer;
import com.urban.app.renderers.Renderer;
import com.urban.simengine.ModelRunner;
import com.urban.simengine.models.Model;

public class Application {
    public static void main(String [] args) {
        Loader loader = new ScenarioALoader();
        Model model = loader.getModel();
        Renderer renderer = new ConsoleLogRenderer(model);
        renderer.start();
        ModelRunner modelRunner = new ModelRunner(model);
        modelRunner.run();
    }
}
