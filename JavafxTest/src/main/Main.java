package main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage stage) {
		Text text = new Text(100, 200, "Hello World!");
		text.setFont(new Font(100));
		Button but = new Button("How are you doing bruh?");
		Scene scene = new Scene(new Group(but));

		stage.setTitle("Welcome to JavaFX!");
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
		stage.centerOnScreen();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}