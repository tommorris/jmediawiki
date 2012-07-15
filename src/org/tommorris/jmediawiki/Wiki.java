package org.tommorris.jmediawiki;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import lombok.Getter;
import lombok.Setter;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Nodes;
import nu.xom.ParsingException;

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

	public String getVersion() throws ParsingException, IOException {
		Document doc = this.loadUrl(this.getApiEndpoint()
				+ "?action=query&meta=siteinfo&siprop=general&format=xml");
		Nodes nodes = doc.getDocument().query("/api/query/general");
		String s = ((Element) nodes.get(0)).getAttributeValue("generator");
		return s;
	}

	private Document loadUrl(String url) throws ParsingException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse response = client.execute(get);
		HttpEntity entity = response.getEntity();
		Builder parser = new Builder();
		Document doc = parser.build(entity.getContent());
		client.getConnectionManager().shutdown();

		return doc;
	}

	/* Wikimedia constructors */

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
