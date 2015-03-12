import static spark.Spark.get;

/**
 * Created by Ben Rasmussen on 3/11/2015.
 */
public class Main {
	public static void main(String[] args) {
		get("/hello", (request, response) -> {
			return "Hello World!";
		});
	}
}
