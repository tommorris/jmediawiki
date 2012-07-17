package org.tommorris.jmediawiki;
import nu.xom.Element;
import lombok.*;

public class Namespace {
	@Getter @Setter private Integer id = null;
	@Getter @Setter private String canonical;
	@Getter @Setter private String subpages;
	@Getter @Setter private String name;
	
	public static Namespace parseFromXml(Element xml) {
		Namespace ns = new Namespace();
		assert(xml.getQualifiedName() == "ns");
		ns.setId(Integer.parseInt(xml.getAttributeValue("id")));
		return ns;
	}
}
