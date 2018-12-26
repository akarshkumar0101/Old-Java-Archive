package main;

import java.util.ArrayList;
import java.util.List;

public class Paper {

	private String title;
	private Researcher[] authors;
	private String paperAbstract;
	private String link;

	private Paper[] references;

	// what future papers reference THIS paper?
	private final List<Paper> resultingPapers;

	public Paper(String title, Researcher[] authors, String paperAbstract, String link, Paper[] references) {
		this.title = title;
		this.authors = authors;
		this.paperAbstract = paperAbstract;
		this.link = link;

		this.references = references;
		for (Paper reference : references) {
			reference.resultingPapers.add(this);
		}

		resultingPapers = new ArrayList<Paper>();
	}

}
