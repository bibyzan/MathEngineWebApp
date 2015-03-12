import com.ben.javacalculator.Equation;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;

/**
 * Tester class for math engine web application
 */
public class Main {
	public static void main(String[] args) {
		get("/hello", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("message", "Hello FreeMarker World");
			return new ModelAndView(attributes, "hello.ftl");
		}, new FreeMarkerTemplateEngine());
	}
}
