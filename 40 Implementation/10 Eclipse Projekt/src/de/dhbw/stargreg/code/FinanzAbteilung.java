package de.dhbw.stargreg.code;

/**
 * 
 * @author fredrik
 *
 */
public class FinanzAbteilung extends Abteilung {
	public class Finanzen {
		private Double kapital;
		
		public Finanzen eroeffnen () {
			return new Finanzen();
		}//eroeffnen
		
		private Finanzen () {
			this.kapital = 1500000.0;
		}//Konstruktor

	
		public void einzahlen (double betrag){
	        if (betrag < 0) {
	            return;
	        }
			this.kapital += betrag;
		}//einzahlen
		
		public String abbuchen (double betrag){
			if (betrag > this.kapital) {
				return ("Eigenkapital zu niedrig!");
			}
			this.kapital -= betrag;
			return null;
		}//abbuchen
		
		public double getKontostand(){
			return this.kapital;
		}//getKontostand
	}
}
