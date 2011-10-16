package de.dhbw.stargreg.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.dhbw.stargreg.code.BauteilMarkt;
import de.dhbw.stargreg.code.BauteilTyp;
import de.dhbw.stargreg.code.PersonalMarkt;
import de.dhbw.stargreg.code.PersonalTyp;
import de.dhbw.stargreg.code.RaumschiffMarkt;
import de.dhbw.stargreg.code.RaumschiffTyp;
import de.dhbw.stargreg.code.Spiel;
import de.dhbw.stargreg.code.SpielRunde;
import de.dhbw.stargreg.code.Unternehmen;

public class SpielTest {
	
	private static Spiel spiel = Spiel.getSpiel();
	
	private static BauteilTyp rumpf = new BauteilTyp("Rumpfbauteil", 1, 100.0, 40.0);
	private static BauteilTyp hitzeschild = new BauteilTyp("Hitzeschild", 2, 200.0, 80.0);
	private static BauteilTyp triebwerk = new BauteilTyp("Triebwerk", 5, 500.0, 200.0);
	private static BauteilTyp geschuetz = new BauteilTyp("Geschütz", 10, 1000.0, 400.0);
	private static BauteilTyp transport = new BauteilTyp("Transportkapsel", 20, 2000.0, 800.0);
	private static BauteilTyp forschung = new BauteilTyp("Forschungsausstattung", 30, 3000.0, 1200.0);
	
	private static RaumschiffTyp xwing = new RaumschiffTyp("X-Wing", 5);
	private static RaumschiffTyp corvette = new RaumschiffTyp ("Correllian Corvette", 10);
	private static RaumschiffTyp falke = new RaumschiffTyp("Millenium Falke", 15);
	
	private static PersonalTyp r2d2 = new PersonalTyp("R2D2", 90);
	private static PersonalTyp kampfDroide = new PersonalTyp("Kampf-Droide", 95);
	private static PersonalTyp droideka = new PersonalTyp("Droideka", 99);
	
	private static Unternehmen galactic = new Unternehmen("Galactic");
	private static Unternehmen foederation = new Unternehmen("Föderation");
	private static Unternehmen rebellen = new Unternehmen("Rebellen");

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		xwing.fuegeBauteilHinzu(rumpf, 18);
		xwing.fuegeBauteilHinzu(hitzeschild, 6);
		xwing.fuegeBauteilHinzu(triebwerk, 4);
		xwing.fuegeBauteilHinzu(geschuetz, 1);
		
		corvette.fuegeBauteilHinzu(rumpf, 38);
		corvette.fuegeBauteilHinzu(hitzeschild, 16);
		corvette.fuegeBauteilHinzu(triebwerk, 6);
		corvette.fuegeBauteilHinzu(transport, 1);
		
		falke.fuegeBauteilHinzu(rumpf, 40);
		falke.fuegeBauteilHinzu(hitzeschild, 30);
		falke.fuegeBauteilHinzu(triebwerk, 10);
		falke.fuegeBauteilHinzu(forschung, 1);
		
		PersonalMarkt.fuegePersonalTypHinzu(r2d2);
		PersonalMarkt.fuegePersonalTypHinzu(kampfDroide);
		PersonalMarkt.fuegePersonalTypHinzu(droideka);
		PersonalMarkt.setSchulungsKosten(r2d2, 300.0);
		PersonalMarkt.setSchulungsKosten(kampfDroide, 400.0);
		
		RaumschiffMarkt.fuegeRaumschiffTypHinzu(xwing);
		RaumschiffMarkt.fuegeRaumschiffTypHinzu(corvette);
		RaumschiffMarkt.fuegeRaumschiffTypHinzu(falke);
		
		BauteilMarkt.fuegeBauteilTypHinzu(rumpf);
		BauteilMarkt.fuegeBauteilTypHinzu(hitzeschild);
		BauteilMarkt.fuegeBauteilTypHinzu(triebwerk);
		BauteilMarkt.fuegeBauteilTypHinzu(geschuetz);
		BauteilMarkt.fuegeBauteilTypHinzu(transport);
		BauteilMarkt.fuegeBauteilTypHinzu(forschung);
		
		// Laufende Kosten und Werbekosten für die drei Personaltypen
		PersonalTyp[] personalTypen = {r2d2, kampfDroide, droideka};
		double[][] personalKosten = {{50, 200}, {100, 400}, {200, 600}};
		// Daten zur Spielrunde: Nachfrage Xwing/Corvette/Falke, Entwicklung Personalkosten
		int[][] daten = {{60, 30, 20, 10},	// 1
						 {66, 30, 24, 10},  // 2
						 {72, 27, 24, 15},  // 3
						 {66, 27, 24, 12},  // 4
						 {78, 36, 28, 10},  // 5
						 {78, 36, 28, 10},  // 6
						 {84, 48, 28,  8},  // 7
						 {84, 48, 28,  9},  // 8
						 {96, 39, 22, 12},  // 9
						 {90, 39, 24, 10}}; // 10
		
		for (int[] i : daten) {
			SpielRunde spielRunde = new SpielRunde();
			spielRunde.getRaumschiffMarkt().setNachfrage(xwing, i[0]);
			spielRunde.getRaumschiffMarkt().setNachfrage(corvette, i[1]);
			spielRunde.getRaumschiffMarkt().setNachfrage(falke, i[2]);
			for (int j=0; j<personalTypen.length; j++) {
				spielRunde.getPersonalMarkt().setLaufendeKosten(personalTypen[j], personalKosten[j][0]);
				spielRunde.getPersonalMarkt().setWerbungsKosten(personalTypen[j], personalKosten[j][1]);
			}
			spiel.fuegeSpielRundeHinzu(spielRunde);
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Spiel.getSpiel().loescheAlles();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSpiel() {
		spiel.fuegeUnternehmenHinzu(galactic);
		spiel.fuegeUnternehmenHinzu(foederation);
		spiel.fuegeUnternehmenHinzu(rebellen);
	}

}
