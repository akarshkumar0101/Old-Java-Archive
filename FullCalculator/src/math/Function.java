package math;

import java.util.HashMap;

/**
 * public so more users can custom useful functions
 * 
 * @author akars
 *
 */
public abstract class Function extends Calculable {

	public Function(HashMap<Character, Double> parentVariableMap) {
		super(parentVariableMap);
	}
}
