package org.tommorris.jmediawiki;
import nu.xom.Element;
import lombok.*;

/**
 * Data class for representing MediaWiki namespaces.
 * 
 * A MediaWiki namespace is a grouping of pages used to split up the wiki into
 * separate sections. For instance, on Wikipedia, namespaces are used to split
 * articles off from talk pages, project pages and so on.
 * 
 * @author tom
 * 
 */
public class Namespace {
	@Getter @Setter private Integer id = null;
	@Getter @Setter private String canonical;
	@Getter @Setter private String subpages;
	@Getter @Setter private String name;
	
	public static Namespace parseFromXml(Element xml) {
		Namespace ns = new Namespace();
		assert(xml.getQualifiedName() == "ns");
		ns.setId(Integer.parseInt(xml.getAttributeValue("id")));
		if (xml.getValue() == null || xml.getValue() == "") {
			ns.setName("");
		} else {
			ns.setName(xml.getValue());
		}
		return ns;
	}
}
