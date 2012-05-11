package com.urban.app.loaders;

import com.urban.simengine.models.Model;

public interface Loader {
    public Model getModel() throws LoaderException;
}
