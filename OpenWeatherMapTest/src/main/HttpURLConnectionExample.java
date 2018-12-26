package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;

import org.json.JSONObject;
import javax.swing.*;


public class HttpURLConnectionExample  {
	
	//private final String USER_AGENT = "Chrome/1.0";
	
	private static String zip;
	
	public static void main(String[] args) throws Exception {
		zip = JOptionPane.showInputDialog("Type in your zip code to find the weather.");
		zip = "72703";
		JSONObject json = getWeather(zip);
		System.out.println(json);
		display(json);
		
		

	}
	
	private static void display(JSONObject inp){
		JFrame frame = new JFrame("Weather");
		JLabel top = new JLabel("Todays weather is: ");
		top.setHorizontalAlignment(SwingConstants.CENTER);
		frame.add(top);
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JSONObject main = (JSONObject) inp.get("main");
		int temp = (int) (((double)main.get("temp") -273)*9/5 +32);
		JLabel label = new JLabel("Temperature: "+temp +" degrees Fahrenheit");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		frame.add(label);
		
		
		frame.setVisible(true);
	}
	

	// HTTP GET request
	private static JSONObject getWeather(String zip) throws Exception {
		
		String urlString = "http://api.openweathermap.org/data/2.5/weather?zip="+zip+",us&appid=2de143494c0b295cca9337e1e96b00e0";
		
		URL url = new URL(urlString);
		System.out.println("Opening connection to "+urlString);
		URLConnection urlcon = url.openConnection();
		HttpURLConnection con =  (HttpURLConnection) urlcon; //http connection

		// optional default is GET
		//con.setRequestMethod("GET");

		//add request header
		//con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("Sending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		
		String inputLine;
		String result = "";

		while ((inputLine = in.readLine()) != null) {
			result = result + inputLine;
		}
		in.close();

		//print result
		//System.out.println(result);
		
        /*BufferedWriter output = null;
        try {
            File file = new File("C:/Users/Akarsh/Desktop/webtest.txt");
            output = new BufferedWriter(new FileWriter(file));
            output.write(result);
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if ( output != null ) output.close();
        }*/
        
        JSONObject jsonObj = new JSONObject(result);
        return jsonObj;
        
	}
}
