package de.dhbw.stargreg.test;

import java.util.Vector;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.dhbw.stargreg.code.Angebot;
import de.dhbw.stargreg.code.Raumschiffmarkt;
import de.dhbw.stargreg.code.Raumschifftyp;
import de.dhbw.stargreg.code.Verkauf;

public class RaumschiffmarktTest {
	
	private static Raumschiffmarkt raumschiffmarkt;
	private static Raumschifftyp xwing;
	private static Raumschifftyp corvette;
	private static int nachfrageXwing = 20;
	private static int nachfrageCorvette = 10;
	
	private Vector<Angebot> angebote;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		xwing = new Raumschifftyp("test", 10) {
			public double getKosten() {
				return 5;
			}
		};
		corvette = new Raumschifftyp("Corvette", 20) {
			public double getKosten() {
				 return 12;
			}
		};
		raumschiffmarkt = new Raumschiffmarkt();		
		raumschiffmarkt.fuegeRaumschifftypHinzu(xwing);
		raumschiffmarkt.fuegeRaumschifftypHinzu(corvette);
		raumschiffmarkt.setNachfrage(xwing, nachfrageXwing);
		raumschiffmarkt.setNachfrage(corvette, nachfrageCorvette);		

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		angebote = new Vector<Angebot>();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBerechneTypAbsatz() {
		angebote.add(new Angebot(xwing, null, 10, 10.0));
		angebote.add(new Angebot(xwing, null, 5, 15.0));
		angebote.add(new Angebot(xwing, null, 10, 20.0));
		
		int gesamtMenge = 0;
		Vector<Verkauf> verkaeufe = raumschiffmarkt.berechneTypAbsatz(xwing, angebote);
		for (Verkauf verkauf : verkaeufe) {
			int menge = verkauf.getMenge();
			Assert.assertTrue(menge >= 0);
			gesamtMenge += menge;
		}
		
		Assert.assertTrue(gesamtMenge <= nachfrageXwing);
	}
	
	@Test
	public void testBerechneGesamtAbsatz() {
		angebote.add(new Angebot(xwing, null, 10, 10.0));
		angebote.add(new Angebot(xwing, null, 5, 15.0));
		angebote.add(new Angebot(xwing, null, 10, 20.0));
		angebote.add(new Angebot(corvette, null, 3, 20.0));
		angebote.add(new Angebot(corvette, null, 5, 18.0));
		angebote.add(new Angebot(corvette, null, 2, 27.0));
		
		Vector<Verkauf> verkaeufe = raumschiffmarkt.berechneGesamtAbsatz(angebote);
		for (Verkauf verkauf : verkaeufe) {
			System.out.printf("%d Stück von %s verkauft\n", verkauf.getMenge(), verkauf.getRaumschifftyp());
		}
	}

}
