package de.demimoranes.realestatecredit.domain;

public class RealEstate {

	protected boolean ownerOccupied;
	protected AmountWithCurrency monthlyRent;
	protected double sizeInSquareMeters;
	
	public RealEstate(boolean ownerOccupied, AmountWithCurrency monthlyRent,
			double sizeInSquareMeters) {
		this.ownerOccupied = ownerOccupied;
		this.monthlyRent = monthlyRent;
		this.sizeInSquareMeters = sizeInSquareMeters;
	}
	
}
