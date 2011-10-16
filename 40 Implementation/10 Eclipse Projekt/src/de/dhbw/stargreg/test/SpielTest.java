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
		
		SpielRunde spielRunde1 = new SpielRunde();
		spielRunde1.getPersonalMarkt().setLaufendeKosten(r2d2, 50);
		spielRunde1.getPersonalMarkt().setLaufendeKosten(kampfDroide, 100);
		spielRunde1.getPersonalMarkt().setLaufendeKosten(droideka, 200);
		spielRunde1.getPersonalMarkt().setWerbungsKosten(r2d2, 200);
		spielRunde1.getPersonalMarkt().setWerbungsKosten(kampfDroide, 400);
		spielRunde1.getPersonalMarkt().setWerbungsKosten(droideka, 600);
		spielRunde1.getRaumschiffMarkt().setNachfrage(xwing, 60);
		spielRunde1.getRaumschiffMarkt().setNachfrage(corvette, 30);
		spielRunde1.getRaumschiffMarkt().setNachfrage(falke, 20);
		
		SpielRunde spielRunde2 = spielRunde1.clone();
		
		//Werte in weiteren Runden anpassen
		//evtl. istBereit-Methode in Märkten, vor der Runde nicht simuliert werden kann
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
		
	}

}
