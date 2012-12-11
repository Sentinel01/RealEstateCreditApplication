package de.demimoranes.realestatecredit.placeholder;

import java.util.Map;

import de.demimoranes.realestatecredit.dslifiers.BusinessObjectDslifier;

public class SimpleValuePlaceholder implements ValuePlaceholder {

	protected final Object value;
	
	public SimpleValuePlaceholder(Object value) {
		this(value, null);
	}
	
	public SimpleValuePlaceholder(Object value, Map<String, ValuePlaceholder> valuePlaceholders) {
		super();
		this.value = value;
		
		if(valuePlaceholders != null) {
			new BusinessObjectDslifier(value).dslify(valuePlaceholders);
		}
	}
	
	@Override
	public Object getValue() {
		return this.value;
	}

}
