package de.dhbw.stargreg.code;

/**
 * Unternehmen machen auf dem Raumschiffmarkt Angebote zum Verkauf von Raumschiffen.
 * Dabei legen sie den Preis fest, für den sie ein bestimmten Raumschifftypen verkaufen möchten.
 * @author fredrik
 *
 */
public class Angebot extends ProduktTransaktion<RaumschiffTyp> {
	/**
	 * Zwischenspeicher für Berechnungen im Raumschiffmarkt.
	 */
	private double anteil;
	
	public Angebot(RaumschiffTyp raumschiffTyp, Unternehmen unternehmen, int menge, double preis) {
		super(raumschiffTyp, unternehmen, menge, preis);
	}
	
	/**
	 * Erzeugt das zum Angebot passende Verkaufsobjekt, wobei sich die Menge unterscheiden kann.
	 * @param menge Menge, die tatsächlich abgesetzt werden kann.
	 * @return Zum Angebot passender Verkauf.
	 */
	public Verkauf kloneVerkauf(int menge) {
		return new Verkauf(getTyp(), this.unternehmen, menge, this.preis);
	}

	public double getAnteil() {
		return anteil;
	}

	public void setAnteil(double anteil) {
		this.anteil = anteil;
	}
}
