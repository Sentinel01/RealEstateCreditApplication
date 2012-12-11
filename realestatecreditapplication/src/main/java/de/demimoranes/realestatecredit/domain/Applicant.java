package de.demimoranes.realestatecredit.domain;

public class Applicant {

	protected AmountWithCurrency takeHomeAmout;
	protected RealEstate domicile;
	
	public Applicant(AmountWithCurrency takeHomeAmout, RealEstate domicile) {
		this.takeHomeAmout = takeHomeAmout;
		this.domicile = domicile;
	}
	
}
