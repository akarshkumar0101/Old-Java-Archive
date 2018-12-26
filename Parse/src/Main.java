import main.java.org.parse4j.Parse;

public class Main {

	public static String APP_ID = "";
	public static String APP_REST_API_ID = "";
	public static String CUSTOM_SERVER_PATH = "";

	public static void main(String[] args) {

		Parse.initialize(APP_ID, APP_REST_API_ID, CUSTOM_SERVER_PATH);

		System.out.println("hi");

	}

}
