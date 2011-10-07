package de.dhbw.stargreg.test;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.dhbw.stargreg.code.Spiel;

public class SpielTest {
	
	private Spiel spiel;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		spiel = new Spiel();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFuegeSpielrundeHinzu() {
		fail("Not yet implemented");
	}

}
