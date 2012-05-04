package com.urban.app;

import com.urban.app.loaders.Loader;
import com.urban.app.loaders.ScenarioALoader;

import javax.swing.*;

public class Application extends JFrame {
    public static void main(String [] args) {
        Loader loader = new ScenarioALoader();
        ModelRunner.run(loader);
    }
}
