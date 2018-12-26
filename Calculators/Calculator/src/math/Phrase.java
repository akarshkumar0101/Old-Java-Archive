package math;

import java.util.ArrayList;

class Phrase extends Calculable{
	ArrayList<Object> parts = new ArrayList<Object>();
	
	//9+5^2*((3+2)/4)+2+(3*4+2);
	
	Phrase(Phrase phrase){
		for(Object o: phrase.parts){
			if(o.getClass() == Number.class)parts.add(new Number((Number)o));
			if(o.getClass() == Operation.class)parts.add((Operation)o);
			if(o.getClass() == Phrase.class)parts.add(new Phrase((Phrase)o));
		}
	}
	
	Phrase(String inp) throws Exception {
		inp = inp.substring(1, inp.length()-1);
		String temp = inp;
		for (int index = 0; index < temp.length();) {
			
			char current = temp.charAt(index);
			
			// par spot
			if (current == '(') {
				int parAfter = 0;
				int i= 0;
				for(i = index+1; i < temp.length(); i++){
					char c = temp.charAt(i);
					
					if (c == '(')
						parAfter++;
					else if (c == ')') {
						if (parAfter > 0) {
							parAfter--;
						}
						else if (parAfter == 0) {// found end parenthesis

							String par = temp.substring(index, i + 1);
							
							//ADDING TO EQUATION (phrase)
							parts.add(new Phrase(par));
							break;
						}
					}
					
				}
				index = i+1;
				continue;
			} 
			
			// op spot
			else if(Operation.getOperation(current) != null){
				//ADDING TO EQUATION (operation)
				parts.add(Operation.getOperation(current));
				index++;
				continue;
			}
			
			// num spot
			else if (Number.isPartOfNumber(current)) {
				int i =0;
				for(i = index+1; i <= temp.length(); i++){
					char c;
					try{
						c= temp.charAt(i);
					}catch(StringIndexOutOfBoundsException e){
						c = 0;
					}
					
					if (!Number.isPartOfNumber(c)|| i == temp.length()) {// found end of number
						
						String num = temp.substring(index, i);
						
						//ADDING TO EQUATION (number)
						parts.add(new Number(num));
						break;
					}
				}
				index = i;
				continue;
			}
			else{
				throw new Exception("something went wrong");
			}
			
			
		}
	}
	
	@Override
	public String toString(){
		String result = "";
		for(Object o: parts.toArray()){
			result = result+o.toString();
		}
		System.gc();
		
		return "("+result+")";
	}
	@Override
	double getValue(){
		Phrase temp = new Phrase(this);
		
		// check for exponents
		for (int i = 0; i < temp.parts.size(); i++) {
			if (temp.parts.get(i) == Operation.POW) {
				Calculable a = (Calculable) temp.parts.get(i - 1);
				Calculable b = (Calculable) temp.parts.get(i + 1);
				Operation op = (Operation) temp.parts.get(i);
				
				System.out.println(a+"     " +op +"     " +b);
				temp.parts.set(i, new Number(op.doOperation(a.getValue(), b.getValue())));
				temp.parts.remove(i - 1);
				temp.parts.remove(i);
				i=i-2;
			}
		}

		// check for multiply/divide
		for (int i = 0; i < temp.parts.size(); i++) {
			if (temp.parts.get(i) == Operation.MUL || temp.parts.get(i) == Operation.DIV) {
				Calculable a = (Calculable) temp.parts.get(i - 1);
				Calculable b = (Calculable) temp.parts.get(i + 1);
				Operation op = (Operation) temp.parts.get(i);
				
				System.out.println(a+"     " +op +"     " +b);
				temp.parts.set(i, new Number(op.doOperation(a.getValue(), b.getValue())));
				temp.parts.remove(i - 1);
				temp.parts.remove(i);
				i=i-2;
			}
		}
		// check for add/subtract
		for (int i = 0; i < temp.parts.size(); i++) {
			if (temp.parts.get(i) == Operation.ADD || temp.parts.get(i) == Operation.SUB) {
				Calculable a = (Calculable) temp.parts.get(i - 1);
				Calculable b = (Calculable) temp.parts.get(i + 1);
				Operation op = (Operation) temp.parts.get(i);
				
				System.out.println(a+"     " +op +"     " +b);
				temp.parts.set(i, new Number(op.doOperation(a.getValue(), b.getValue())));
				temp.parts.remove(i - 1);
				temp.parts.remove(i);
				i=i-2;
			}
		}
		
		
		return ((Calculable)temp.parts.get(0)).getValue();
	}



}
