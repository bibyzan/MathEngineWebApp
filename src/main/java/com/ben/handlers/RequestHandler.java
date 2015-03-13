package com.ben.handlers;

import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
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
	}
}
