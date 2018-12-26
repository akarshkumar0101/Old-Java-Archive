package math.cas;

import java.util.Map;

import array.Arrays;
import math.cas.function.basicfunction.MulFunction;

//will be of type variable, constant, or function
public abstract class Entity {

	protected Variable[] variables = new Variable[] {};

	protected Entity(Entity... dependancies) {
		for (Entity dep : dependancies) {
			for (Variable var : dep.variables) {
				if (!Arrays.contains(variables, var)) {
					variables = Arrays.addElement(variables, var, new Variable[variables.length + 1]);
				}
			}
		}
	}

	public Variable[] variableDependancies() {
		return variables;
	}

	public Entity scale(Constant constant) {
		return new MulFunction(constant, this);
	}

	public abstract double evaluate(Map<Variable, Double> variableValues);

	public abstract Entity partialWithRespectTo(Variable var);

	public boolean isConstant() {
		return variables.length == 0;
	}

	public abstract Entity consolidate();

}
