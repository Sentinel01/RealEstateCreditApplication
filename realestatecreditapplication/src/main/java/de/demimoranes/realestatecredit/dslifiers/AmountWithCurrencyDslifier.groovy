package de.demimoranes.realestatecredit.dslifiers

import de.demimoranes.realestatecredit.domain.AmountWithCurrency;

class AmountWithCurrencyDslifier {

	public void dslify() {
		AmountWithCurrency.metaClass {
			plus { AmountWithCurrency operand2 ->
				if(delegate.currency == operand2.currency) {
					new AmountWithCurrency(delegate.amount + operand2.amount, delegate.currency);
				}
			}
			
			minus { AmountWithCurrency operand2 ->
				if(delegate.currency == operand2.currency) {
					new AmountWithCurrency(delegate.amount - operand2.amount, delegate.currency);
				}
			}
			
			div { Number operand2 ->
				new AmountWithCurrency(delegate.amount / operand2, delegate.currency);
			}
		}
	}
	
}
