package de.demimoranes.realestatecredit.dslifiers

import java.util.Map;

import de.demimoranes.realestatecredit.placeholder.ValuePlaceholder;

class BusinessObjectDslifier {

	private final Object businessObject;
	
	public BusinessObjectDslifier(Object businessObject) {
		this.businessObject = businessObject;
	}
	
	public void dslify(Map<String, ValuePlaceholder> valuePlaceholders) {
		businessObject.metaClass {
			getProperty { String name -> 
				ValuePlaceholder vp = valuePlaceholders[name]
				if(vp) {
					vp.value;
				}
				else {
					def metaProperty = delegate.metaClass.getMetaProperty(name)
					if(metaProperty) {
						metaProperty.getProperty(delegate);
					}
					else {
						delegate.@"$name";
					}
				}
			}
		}
	}
	
}
