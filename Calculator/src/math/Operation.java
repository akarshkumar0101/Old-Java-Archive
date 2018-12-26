package math;

enum Operation {
	POW, MUL, DIV, ADD, SUB;
	
	
	double doOperation(double a, double b){
		if(this == POW) return Math.pow(a, b);
		if(this == MUL) return a*b;
		if(this == DIV) return a/b;
		if(this == ADD) return a+b;
		if(this == SUB) return a-b;
		return 0;
	}
	
	static Operation getOperation(char c){
		
		if(c == '^') return POW;
		if(c == '*') return MUL;
		if(c == '/') return DIV;
		if(c == '+') return ADD;
		if(c == '-') return SUB;
		
		return null;
	}
	
	@Override
	public String toString(){
		if(this == POW) return "^";
		if(this == MUL) return "*";
		if(this == DIV) return "/";
		if(this == ADD) return "+";
		if(this == SUB) return "-";
		return null;
	}
}
