package org.tommorris.jmediawiki.test;

import static org.junit.Assert.*;

import java.io.IOException;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

import org.junit.Test;
import org.tommorris.jmediawiki.Namespace;

public class NamespaceTest {
	@Test
	public void testXmlParse() throws ValidityException, ParsingException {
		/* First, let's use our main namespace. */
		String nss0 = "<?xml version=\"1.0\"?>\n<ns id=\"0\" case=\"first-letter\" content=\"\" xml:space=\"preserve\"/>";
		Element el0 = NamespaceTest.processXml(nss0);
		Namespace ns0 = Namespace.parseFromXml(el0);
		assertNotNull(ns0);
		assertEquals(new Integer(0), ns0.getId());
		assertEquals("", ns0.getName());
	}
	
	public static Element processXml(String input) throws ValidityException, ParsingException {
		try {
			Builder parser = new Builder();
			Document doc = parser.build(input, "");
			return doc.getRootElement();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
