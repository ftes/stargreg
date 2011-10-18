package de.dhbw.stargreg.code;

/**
 * 
 * @author Britta
 *
 */
public class FinanzAbteilung extends Abteilung {
		private Double kontostand= 0.0;
/*		
		public Finanzen eroeffnen () {
			return new Finanzen();
		}//eroeffnen
*/		
		public FinanzAbteilung () {
			this.kontostand = 1500000.0;
		}//Konstruktor

		public void einzahlen (double betrag){
	        if (betrag < 0) {
	            return;
	        }
			this.kontostand += betrag;
		}//einzahlen
		
		public String abbuchen (double betrag){ // Rückgabewert String ???
			if (betrag > this.kontostand) {
				return ("Eigenkapital zu niedrig!");
			}
			this.kontostand -= betrag;
			return null;
		}//abbuchen
		
		public double getKontostand(){
			return this.kontostand;
		}//getKontostand
}//FinanzAbteilung
