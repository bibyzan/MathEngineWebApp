package com.ben.transformers;

import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * Transforms objects into JSON strings
 */
public class JsonTransformer implements ResponseTransformer{

    /**
     * An instance of {@code Gson}
     */
    private Gson gson = new Gson();


    /**
     * {@inheritDoc}
     */
    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }
}
