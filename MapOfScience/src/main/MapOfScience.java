package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MapOfScience implements Serializable {

	private static final long serialVersionUID = 7407042891862406882L;

	public static MapOfScience map;
	public final Topic mainTopic;

	private static final File saveFile = new File("map.ser");
	static {

		try {
			FileInputStream fileIn = new FileInputStream(saveFile);
			ObjectInputStream in = new ObjectInputStream(fileIn);

			map = (MapOfScience) in.readObject();

			in.close();
			fileIn.close();
		} catch (Exception e) {
			System.err.println("Could not load map from file");
			map = new MapOfScience();
		}

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					FileOutputStream fileOut = new FileOutputStream(saveFile);
					ObjectOutputStream out = new ObjectOutputStream(fileOut);

					out.writeObject(map);

					out.close();
					fileOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private MapOfScience() {
		mainTopic = new Topic("Academia", null);
	}

	public static void main(String[] args) {
		String input = null;

		Scanner scan = new Scanner(System.in);
		Topic topic = map.mainTopic;
		do {
			if (input != null) {
				String[] inps = input.split(" ");
				if (input.startsWith("enter")) {
					topic = topic.findChildTopic(inps[1]);
				}
				if (input.startsWith("add")) {
					topic.addChildTopic(new Topic(inps[1], topic));
				}
				if (input.startsWith("remove")) {
					topic.removeChildTopic(topic.findChildTopic(inps[1]));
				}
			}
			System.out.println("\n\n");
			System.out.println("Press enter [topic], add [topic], remove [topic], or exit");
			System.out.println("Current topic: " + topic);
			System.out.println("Child topics: ");
			for (Topic child : topic.getChildren()) {
				System.out.println("\t" + child.getName());
			}

			input = scan.nextLine();

		} while (!input.startsWith("exit"));

		scan.close();
	}
}

class Topic implements Serializable {
	private static final long serialVersionUID = -7969937135887107281L;

	private final String name;
	private final Topic parent;
	private final List<Topic> children;

	public Topic(String name, Topic parent) {
		this.name = name;
		this.parent = parent;
		children = new ArrayList<Topic>();
	}

	public void addChildTopic(Topic topic) {
		children.add(topic);
	}

	public Topic findChildTopic(String name) {
		for (Topic top : children) {
			if (top.getName().equals(name))
				return top;
		}
		return null;
	}

	public void removeChildTopic(Topic topic) {
		children.remove(topic);
	}

	public String getName() {
		return name;
	}

	public Topic getParent() {
		return parent;
	}

	public List<Topic> getChildren() {
		return children;
	}

	@Override
	public String toString() {
		return name;
	}

}
