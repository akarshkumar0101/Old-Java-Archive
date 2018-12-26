package math;

public class CalMath {
	
	public static double getValue(String inp){
		inp = inp.replaceAll(" ", "");
		inp = "("+inp+")";
		Phrase p = null;
		try {
			p = new Phrase(inp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Problem: " +p);
		return p.getValue();
	}
	
}
