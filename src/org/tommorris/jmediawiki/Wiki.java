package org.tommorris.jmediawiki;

import java.io.IOException;
import java.util.ArrayList;

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
 * Instantiate the class with a URL to a MediaWiki installation,
 * or use the convenience methods - e.g. <code>Wiki.wikipedia("en")</code>
 * to retrieve a Wikimedia Foundation project.
 * 
 * @author tom
 */
public class Wiki {
	@Getter @Setter private String rootUrl;
	@Getter @Setter private String name;
	@Getter @Setter private String apiEndpoint;
	@Getter @Setter private String lang;
	@Getter private String articlePath;
	private String mainPage;
	private String version;
	private String gitHash;
	private String phpVersion;
	private ArrayList<Namespace> namespaces;
	
	private boolean loadedSiteInfo = false;
	
	private void loadSiteInfo() throws ParsingException, IOException {
		if (loadedSiteInfo == false) {
			Document doc = this.loadUrl(this.getApiEndpoint()
				+ "?action=query&meta=siteinfo&siprop=general&format=xml");
			Nodes nodes = doc.getDocument().query("/api/query/general");
			Element generalElement = (Element) nodes.get(0);
			this.mainPage = generalElement.getAttributeValue("mainpage");
			this.version = generalElement.getAttributeValue("generator");
			this.gitHash = generalElement.getAttributeValue("git-hash");
			this.phpVersion = generalElement.getAttributeValue("phpversion");
			// This overrides the site name with a more accurate description.
			this.setName(generalElement.getAttributeValue("sitename"));
			
			Nodes namespaces = doc.getDocument().query("/api/query/namespaces/ns");
			ArrayList<Namespace> nsList = new ArrayList<Namespace>();
			for (int i = 0; i < namespaces.size(); i++) {
				Namespace ns = Namespace.parseFromXml((Element) namespaces.get(i));
				nsList.add(ns);
			}
			this.namespaces = nsList;
			this.loadedSiteInfo = true;
		}
	}

	public String getVersion() throws ParsingException, IOException {
		this.loadSiteInfo();
		return this.version;
	}
	
	public String getMainPage() throws ParsingException, IOException {
		this.loadSiteInfo();
		return this.mainPage;
	}
	
	public String getGitHash() throws ParsingException, IOException {
		this.loadSiteInfo();
		return this.gitHash;
	}
	
	public String getPhpVersion() throws ParsingException, IOException {
		this.loadSiteInfo();
		return this.phpVersion;
	}
	
	public ArrayList<Namespace> getNamespaces() throws ParsingException, IOException {
		this.loadSiteInfo();
		return this.namespaces;
	}

	/* Convenience methods */
	
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
