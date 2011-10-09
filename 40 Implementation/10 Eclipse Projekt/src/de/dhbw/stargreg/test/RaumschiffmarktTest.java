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

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		raumschiffmarkt = new Raumschiffmarkt();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBerechneTypAbsatz() {
		Raumschifftyp raumschifftyp = new Raumschifftyp("test", 10) {
			public double getKosten() {
				return 5;
			}
		};
		int nachfrage = 20;
		
		raumschiffmarkt.setNachfrage(raumschifftyp, nachfrage);
		Vector<Angebot> angebote = new Vector<Angebot>();
		angebote.add(new Angebot(raumschifftyp, null, 10, 10.0));
		angebote.add(new Angebot(raumschifftyp, null, 5, 15.0));
		angebote.add(new Angebot(raumschifftyp, null, 10, 20.0));
		
		int gesamtMenge = 0;
		Vector<Verkauf> verkaeufe = raumschiffmarkt.berechneTypAbsatz(raumschifftyp, angebote);
		for (Verkauf verkauf : verkaeufe) {
			int menge = verkauf.getMenge();
			Assert.assertTrue(menge >= 0);
			gesamtMenge += menge;
		}
		
		Assert.assertTrue(gesamtMenge <= nachfrage);
	}

}
