package de.dhbw.stargreg.code;

public final class BauteilTyp extends ProduktTyp {
	/**
	 * Grundpreis, von dem der Marktpreis abhängt
	 */
	private Double grundPreis;
	
	/**
	 * Betrag, um den der Marktpreis maximal vom Grundpreis abweichen darf
	 */
	private Double maxPreisDelta;
	
	public BauteilTyp(String name, int lagerplatzEinheiten, Double grundPreis, Double maxPreisDelta){
		super(name, lagerplatzEinheiten);
		this.grundPreis = grundPreis;
		this.maxPreisDelta = maxPreisDelta;
	}
	
	public Double getGrundPreis(){
		return grundPreis;
	}
	
	public Double getMaxPreisDelta() {
		return maxPreisDelta;
	}
}
