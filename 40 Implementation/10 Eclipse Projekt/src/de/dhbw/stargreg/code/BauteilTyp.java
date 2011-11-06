package de.dhbw.stargreg.code;

/**
 * Raumschiffe setzen sich aus Bauteilen zusammen. Deren Preis ist variabel, dabei
 * gibt es einen Basispreis und eine maximale Abweichung hiervon. Je nach verkaufter Menge
 * in der Vorrunde und dem Verhältnis dieser Mengen untereinander errechnet sich dann der
 * neue Bauteilpreis.
 * @author fredrik
 *
 */
public final class BauteilTyp extends ProduktTyp {
	public enum Art {STANDARD, SONDER};
	
	private final Art art;
	
	/**
	 * Grundpreis, von dem der Marktpreis abhängt.
	 */
	private final double grundPreis;
	
	/**
	 * Betrag, um den der Marktpreis maximal vom Grundpreis abweichen darf.
	 */
	private final double maxPreisDelta;
	
	private double preis;
	
	public BauteilTyp(String name, int lagerplatzEinheiten, Double grundPreis, Double maxPreisDelta, Art art){
		super(name, lagerplatzEinheiten);
		this.grundPreis = grundPreis;
		this.maxPreisDelta = maxPreisDelta;
		this.preis = grundPreis;
		this.art = art;
	}
	
	public Art getArt() {
		return art;
	}
	
	public Double getPreis() {
		return preis;
	}
	
	/**
	 * Berechnet den neuen Preis für die nächste Runde abhängig von der Abweichung.
	 * @param abweichung Prozentuale Abweichung des Umsatzes mit diesem Bauteiltyp vom
	 * durchschnittlichen Umsatz aller Bauteiltypen.
	 */
	public void berechnePreis(double abweichung) {
		preis = grundPreis - maxPreisDelta
				+ 2 * Math.pow(maxPreisDelta, 2)
				/ (maxPreisDelta
						+ maxPreisDelta
							/ (Math.pow(2 / maxPreisDelta + 1,
									2 * abweichung * maxPreisDelta)));
	}
	
	public double getGrundPreis() {
		return grundPreis;
	}
}
