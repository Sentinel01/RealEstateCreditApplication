package de.demimoranes.realestatecredit.dslifiers

import de.demimoranes.realestatecredit.domain.Applicant;

class ApplicantDslifier {
	
	public void dslify() {
		Applicant.metaClass {
			getMonatlichesNettoeinkommen {
				delegate.takeHomeAmout;
			}
			
			getMonatlicheMiete {
				delegate.domicile.monthlyRent
			}
		}
	}
	
}
