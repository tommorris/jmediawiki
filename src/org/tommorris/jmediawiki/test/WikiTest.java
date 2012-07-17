package org.tommorris.jmediawiki.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import nu.xom.ParsingException;

import org.junit.Test;
import org.tommorris.jmediawiki.Namespace;
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

	@Test
	public void testMetadata() {
		try {
			Wiki local = new Wiki();
			local.setName("localhost");
			local.setRootUrl("http://localhost:4881/");
			local.setApiEndpoint("http://localhost:4881/api.php");
			assertNotNull(local.getVersion());
			assertNotNull(local.getGitHash());
		} catch (ParsingException e) {
			e.printStackTrace();
			fail("threw ParsingException");
		} catch (IOException e) {
			e.printStackTrace();
			fail("threw IOException");
		}
	}
	
	@Test
	public void testSiteNameChange() {
		try {
			Wiki local = new Wiki();
			local.setName("localhost");
			local.setRootUrl("http://localhost:4881");
			local.setApiEndpoint("http://localhost:4881/api.php");
			local.getVersion();
			assertNotSame("localhost", local.getName());
		} catch (ParsingException e) {
			e.printStackTrace();
			fail("threw ParsingException");
		} catch (IOException e) {
			e.printStackTrace();
			fail("threw IOException");
		}
	}
	
	@Test
	public void testNamespaces() {
		try {
			Wiki local = new Wiki();
			local.setName("localhost");
			local.setRootUrl("http://localhost:4881");
			local.setApiEndpoint("http://localhost:4881/api.php");
			ArrayList<Namespace> namespaces = local.getNamespaces();
			assertNotNull(namespaces);
			for (Namespace namespace : namespaces) {
				assertNotNull(namespace);
			}
		} catch (ParsingException e) {
			e.printStackTrace();
			fail("threw ParsingException");
		} catch (IOException e) {
			e.printStackTrace();
			fail("threw IOException");
		}
	}
}
