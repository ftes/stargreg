package de.dhbw.stargreg.code;

/**
 * Unternehmen machen auf dem Raumschiffmarkt Angebote zum Verkauf von Raumschiffen.
 * Dabei legen sie den Preis fest, für den sie ein bestimmten Raumschifftypen verkaufen.
 * @author fredrik
 *
 */
public class Angebot extends Transaktion {
	/**
	 * Zwischenspeicher für Berechnungen im Raumschiffmarkt
	 */
	private double anteil;
	
	public Angebot(RaumschiffTyp raumschifftyp, Unternehmen unternehmen, int menge, double preis) {
		super(raumschifftyp, unternehmen, menge, preis);
	}
	
	public RaumschiffTyp getRaumschifftyp() {
		return (RaumschiffTyp) typ;
	}
	
	/**
	 * Erzeugt das zum Angebot passende Verkaufsobjekt, wobei sich die Menge unterscheiden kann
	 * @param menge Menge, die tatsächlich abgesetzt werden kann
	 * @return Zum Angebot passender Verkauf
	 */
	public Verkauf kloneVerkauf(int menge) {
		return new Verkauf(getRaumschifftyp(), this.unternehmen, menge, this.preis);
	}

	public double getAnteil() {
		return anteil;
	}

	public void setAnteil(double anteil) {
		this.anteil = anteil;
	}
}
