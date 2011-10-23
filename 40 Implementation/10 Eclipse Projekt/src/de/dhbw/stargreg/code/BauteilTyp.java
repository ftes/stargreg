package de.dhbw.stargreg.code;

public final class BauteilTyp extends ProduktTyp {
	/**
	 * Grundpreis, von dem der Marktpreis abhängt
	 */
	private final double grundPreis;
	
	/**
	 * Betrag, um den der Marktpreis maximal vom Grundpreis abweichen darf
	 */
	private final double maxPreisDelta;
	
	private double preis;
	
	public BauteilTyp(String name, int lagerplatzEinheiten, Double grundPreis, Double maxPreisDelta){
		super(name, lagerplatzEinheiten);
		this.grundPreis = grundPreis;
		this.maxPreisDelta = maxPreisDelta;
		this.preis = grundPreis;
	}
	
	public Double getPreis() {
		return preis;
	}
	
	/**
	 * Berechnet den neuen Preis des jeweiligen Bauteils
	 * @param abweichung
	 */
	public void berechnePreis(double abweichung) {
		preis = grundPreis - maxPreisDelta
				+ 2 * Math.pow(maxPreisDelta, 2)
				/ (maxPreisDelta
						+ maxPreisDelta
							/ (Math.pow(2 / maxPreisDelta + 1,
									2 * abweichung * maxPreisDelta)));
	}
}
