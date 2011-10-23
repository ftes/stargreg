package de.dhbw.stargreg.code;

/**
 * Finanzabteilung eines Unternehmens.
 * @author Britta
 *
 */
public class FinanzAbteilung extends Abteilung {
	private double kapital = 0;

	public FinanzAbteilung(Unternehmen unternehmen) {
		super(unternehmen);
	}

	public void einzahlen (double betrag){
		if (betrag < 0) {
			return;
		}
		this.kapital += betrag;
	}

	public boolean abbuchen (double betrag){
		if (betrag > this.kapital) {
			System.err.println("Kapital zu niedrig");
			return false;
		}
		this.kapital -= betrag;
		return true;
	}

	public double getKontostand(){
		return this.kapital;
	}
	
	/**
	 * Kosten bei Überziehung berechnen. Dies ist nötig, weil beispielsweise unklar
	 * ist, wie viele Raumschiffe gelagert werden, da dies von der absetzbaren menge
	 * abhängig ist.
	 */
	public void simuliere() {
		
	}

}
