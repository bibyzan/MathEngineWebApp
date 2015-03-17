package com.ben.handlers;

import com.ben.javacalculator.Equation;
import com.ben.transformers.EquationTransformer;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.setPort;

/**
 * Handles JSON and HTML requests
 */
public class RequestHandler {
	public static void main(String[] args) {
		setPort(3000);

        // Handle the URL "/" by returning the index view
        get("/", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("message", "Buster Brown");
			return new ModelAndView(attributes, "index.ftl");
		}, new FreeMarkerTemplateEngine());

        // Handle the URL "/calculate" by performing a calculation
        // and returning the result as JSON
		post("/calculate", "application/json", (request,response) -> {
            Equation equation = new Equation(request.queryParams("equation"));
			response.type("application/json");
			return EquationTransformer.transform(equation);
		});
	}
}
