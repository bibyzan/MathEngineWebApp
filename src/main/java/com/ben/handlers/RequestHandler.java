package com.ben.handlers;

import com.ben.javacalculator.Equation;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.setPort;

/**
 * Tester class for math engine web application
 */
public class RequestHandler {
	public static void main(String[] args) {
		setPort(3000);

        get("/hello", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("message", "Buster Brown");
			return new ModelAndView(attributes, "hello.ftl");
		}, new FreeMarkerTemplateEngine());

		post("/calculate", "application/json",(request,response) -> {
			Equation equation = new Equation(request.params("equation"));
			response.type("application/json");
			return equation.toString();
		});
	}
}
