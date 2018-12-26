package question;

public enum Choice { // ALL CHOICES GO HERE NO MATTER WHAT THE QUESTION
    
    O1, O2, O3, O4, O5, O6, O7, O8, O9, O10;

    @Override
    public String toString() {
    	if(this == O1){
    		return "Option 1";
    	}
    	if(this == O2){
    		return "Option 2";
    	}
    	if(this == O3){
    		return "Option 3";
    	}
    	if(this == O4){
    		return "Option 4";
    	}
    	if(this == O5){
    		return "Option 5";
    	}
    	if(this == O5){
    		return "Option 5";
    	}
    	if(this == O7){
    		return "Option 7";
    	}
    	if(this == O8){
    		return "Option 8";
    	}
    	if(this == O9){
    		return "Option 9";
    	}
    	if(this == O10){
    		return "Option 10";
    	}
    	
    	
    	else return "Unregistered option";
    }
}
