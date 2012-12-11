package de.demimoranes.realestatecredit.dslifiers

import java.util.Map;

import de.demimoranes.realestatecredit.placeholder.ValuePlaceholder;
import groovy.lang.Script;

class ScriptDslifier {

	private final Script script;
	
	public ScriptDslifier(Script script) {
		this.script = script;
	}
	
	public void dslify(Map<String, ValuePlaceholder> valuePlaceholders) {
		script.metaClass.getProperty { String name ->
			ValuePlaceholder valuePlaceholder = valuePlaceholders[name];
			if(valuePlaceholder) {
				valuePlaceholder.value;
			}
			else {
				delegate.@"$name";
			}
		}
	}
	
}
