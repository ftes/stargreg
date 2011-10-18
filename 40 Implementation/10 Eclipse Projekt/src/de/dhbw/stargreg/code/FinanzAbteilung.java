package de.dhbw.stargreg.code;

/**
 * 
 * @author Britta
 *
 */
public class FinanzAbteilung extends Abteilung {
	public FinanzAbteilung(Unternehmen unternehmen) {
		super(unternehmen);
	}
	private double kapital = 0;

	//kein fest gecodetes Startguthaben!!!

	public void einzahlen (double betrag){
		if (betrag < 0) {
			return;
		}
		this.kapital += betrag;
	}//einzahlen

	public boolean abbuchen (double betrag){
		if (betrag > this.kapital) {
			System.err.println("Kapital zu niedrig");
			return false;
		}
		this.kapital -= betrag;
		return true;
	}//abbuchen

	public double getKontostand(){
		return this.kapital;
	}//getKontostand
	
	/**
	 * Kosten bei Überziehung berechnen. Dies ist nötig, weil beispielsweise unklar
	 * ist, wie viele Raumschiffe gelagert werden, da dies von der absetzbaren menge
	 * abhängig ist.
	 */
	public void simuliere() {
		
	}

}
