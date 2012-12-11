package de.demimoranes.realestatecredit;

import java.util.Map;

import org.codehaus.groovy.control.CompilerConfiguration;

import de.demimoranes.realestatecredit.dslifiers.ScriptDslifier;
import de.demimoranes.realestatecredit.placeholder.ValuePlaceholder;

import groovy.lang.GroovyShell;
import groovy.lang.Script;

public class GroovyFormula {

	protected final String name;
	protected final String code;
	
	private Script script;
	private final Map<String, ValuePlaceholder> valuePlaceholders;
	private GroovyShell groovyShell;
	
	public GroovyFormula(String name, String code, CompilerConfiguration compilerConfiguration, Map<String, ValuePlaceholder> valuePlaceholders) {
		super();
		this.name = name;
		this.code = code;
		this.valuePlaceholders = valuePlaceholders;
		
		this.groovyShell = new GroovyShell(compilerConfiguration);
	}
	
	public synchronized Object execute() {
		if(this.script == null) {
			this.script = this.groovyShell.parse(this.code);
		}
		
		new ScriptDslifier(script).dslify(valuePlaceholders);
		return this.script.run();
	}
	
}
