package com.urban.app;

import com.urban.app.loaders.Loader;
import com.urban.app.loaders.ScenarioALoader;

public class Application {
    public static void main(String [] args) {
        Loader loader = new ScenarioALoader();
        ModelRunner.run(loader);
    }
}
