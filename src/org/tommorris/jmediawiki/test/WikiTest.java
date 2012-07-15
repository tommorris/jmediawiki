package org.tommorris.jmediawiki.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.tommorris.jmediawiki.Wiki;

public class WikiTest {

	@Test
	public void testWikipediaConstruction() {
		Wiki wp = Wiki.wikipedia("en");
		assertEquals("https://en.wikipedia.org/", wp.getRootUrl());
		assertEquals("https://en.wikipedia.org/w/api.php", wp.getApiEndpoint());
	}
	
	@Test
	public void testLanguageConstruction() {
		Wiki wp = Wiki.wikipedia("en");
		assertEquals("en", wp.getLang());
		Wiki commons = Wiki.commons();
		assertEquals(null, commons.getLang());
	}

}
