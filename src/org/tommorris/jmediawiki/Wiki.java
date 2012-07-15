package org.tommorris.jmediawiki;

import lombok.Getter;
import lombok.Setter;

/**
 * Root class that represents a MediaWiki wiki and endpoint.
 * 
 * @author tom
 */
public class Wiki {
	@Getter @Setter private String rootUrl;
	@Getter @Setter private String name;
	@Getter @Setter private String apiEndpoint;
	@Getter @Setter private String lang;
	
	public static Wiki wikipedia(String lang) {
		Wiki wp = new Wiki();
		wp.setName("Wikipedia");
		wp.setLang(lang);
		Wiki.makeProjectUrl(wp, lang, "wikipedia");
		return wp;
	}
	
	public static Wiki wikinews(String lang) {
		Wiki wp = new Wiki();
		wp.setName("Wikinews");
		wp.setLang(lang);
		Wiki.makeProjectUrl(wp, lang, "wikinews");
		return wp;
	}

	public static Wiki wikibooks(String lang) {
		Wiki wp = new Wiki();
		wp.setName("Wikibooks");
		wp.setLang(lang);
		Wiki.makeProjectUrl(wp, lang, "wikibooks");
		return wp;
	}
	
	public static Wiki wikiquote(String lang) {
		Wiki wp = new Wiki();
		wp.setName("Wikiquote");
		wp.setLang(lang);
		Wiki.makeProjectUrl(wp, lang, "wikiquote");
		return wp;
	}
	
	public static Wiki wikisource(String lang) {
		Wiki wp = new Wiki();
		wp.setName("Wikisource");
		wp.setLang(lang);
		Wiki.makeProjectUrl(wp, lang, "wikisource");
		return wp;
	}
	
	public static Wiki wikiversity(String lang) {
		Wiki wp = new Wiki();
		wp.setName("Wikiversity");
		wp.setLang(lang);
		Wiki.makeProjectUrl(wp, lang, "wikiversity");
		return wp;
	}
	
	public static Wiki wikispecies() {
		Wiki wp = new Wiki();
		wp.setName("Wikispecies");
		Wiki.makeProjectUrl(wp, "species", "wikimedia");
		return wp;
	}
	
	public static Wiki commons() {
		Wiki wp = new Wiki();
		wp.setName("Wikimedia Commons");
		Wiki.makeProjectUrl(wp, "commons", "wikimedia");
		return wp;
	}
	
	/**
	 * Used to construct URLs for internal Wikimedia projects.
	 * 
	 * @param wiki
	 * @param lang
	 * @param project
	 */
	private static void makeProjectUrl(Wiki wiki, String lang, String project) {
		String url = String.format("https://%s.%s.org/", lang, project);
		wiki.setRootUrl(url);
		wiki.setApiEndpoint(wiki.getRootUrl() + "w/api.php");
	}
}
