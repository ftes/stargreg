package de.dhbw.stargreg.code;

/**
 * 
 * @author Britta
 *
 */
public class FinanzAbteilung extends Abteilung {
	public class Finanzen {
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
	}
}
