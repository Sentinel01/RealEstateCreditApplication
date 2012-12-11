package de.demimoranes.realestatecredit.dslifiers

import de.demimoranes.realestatecredit.domain.RealEstate;

class RealEstateDslifier {

	public void dslify() {
		RealEstate.metaClass {
			getMonatlicheMiete {
				delegate.monthlyRent
			}
			
			getGröße {
				delegate.sizeInSquareMeters
			}
			
			isSelbstgenutzt {
				delegate.ownerOccupied
			}
		}
	}
	
}
