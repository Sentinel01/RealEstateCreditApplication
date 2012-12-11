package de.demimoranes.realestatecredit.placeholder;

import de.demimoranes.realestatecredit.GroovyFormula;

public class GroovyFormulaValuePlaceholder implements ValuePlaceholder {

	protected final GroovyFormula formula;
	
	public GroovyFormulaValuePlaceholder(GroovyFormula formula) {
		super();
		this.formula = formula;
	}
	
	@Override
	public Object getValue() {
		return formula.execute();
	}

}
