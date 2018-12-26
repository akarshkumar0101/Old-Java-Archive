package question;

public class Question {
	
	public static final Question[] QUESTIONS = new Question[10];
	
	private final String question;
	
	private final String[] displayAnswers;
	
	private final Choice[] availableResponses;
	
	private final int weight;// SHOULD I INCLUDE WEIGHTED QUESTIONS?
	
	public static final int TOTALWEIGHT;
	//private final boolean multipleChoice; //WON'T HAVE THIS BECAUSE IT MAKES THIS PROGRAM NOT FUN TO THE USER
	

	static {
		// declare all questions here
		QUESTIONS[0] = new Question(
				"Question 1: Which form of social media do you use the most?",70,
				new String[]{"Facebook", "Instagram", "Snapchat", "Twitter", "Google+", "I don't use social media"});
		QUESTIONS[1] = new Question(
				"Question 2: Which holiday do you enjoy the most?", 100,
				new String[]{"Christmas", "Halloween", "Thanksgiving","Valentine's Day"});
		QUESTIONS[2] = new Question(
				"Question 3: What is your idea of a night out?", 100,
				new String[]{"Movies", "Concert", "Dinner at a resturant"});
		QUESTIONS[3] = new Question(
				"Question 4: What is your favorite school subject?", 90,
				new String[]{"History", "English", "Math", "Science", "The Arts", "PE/Sports"});
		QUESTIONS[4] = new Question(
				"Question 5: What is your favorite fast food?", 70,
				new String[]{"McDonald's", "Sonic","Taco Bell", "Subway"});
		QUESTIONS[5] = new Question(
				"Question 6: Which of the following would you rather do?", 160,
				new String[]{"Take a peaceful walk in the park", "Go to a sports event",
						"Stay inside"});
		QUESTIONS[6] = new Question(
				"Question 7: What is your favorite kind of music?", 130,
				new String[]{"Rap", "Classical", "Hip hop","Jazz", "Blues","I don't listen to music"});
		QUESTIONS[7] = new Question(
				"Question 8: How do you imagine yourself to be when you've grown up?",160,
				new String[]{"Happily settled down somewhere", "Always on a new adventure", 
						"Always inventing something new"});
		QUESTIONS[8] = new Question(
				"Question 9: What is your favorite genre of TV shows/movies?", 120,
				new String[]{"Comedy", "Science-fiction", "Horror", "Drama"});
		QUESTIONS[9] = new Question(
				"Question 10: What is your favorite sport to play/watch?", 80,
				new String[]{"Basketball", "Football","Tennis","Soccer", "I hate sports"});
		int temp =0;
		for(Question q: QUESTIONS){
			temp += q.getWeight();
		}
		TOTALWEIGHT = temp;
	}
	// LOCK CONSTRUCTOR
	private Question(String question,int weight, String []displayAnswers){
		this.question = question;
		this.weight = weight;
		
		this.displayAnswers = displayAnswers;
		availableResponses = new Choice[displayAnswers.length];
		
		Choice[] choices = Choice.values();
		
		for(int i=0; i <availableResponses.length; i ++){
			availableResponses[i] = choices[i];
		}
		
	}
	
	public String getQuestion(){
		return question;
	}
	public Choice[] getAvailableResponses(){
		return availableResponses;
	}
	public String[] getDisplayAnswers(){
		return displayAnswers;
	}
	public int getWeight(){
		return weight;
	}
	

}
