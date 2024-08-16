package ${package}.web;

import java.util.HashMap;
import java.util.Map;
import org.apache.wicket.markup.html.WebPage;
import ${package}.web.gui.HomePage;
import ${package}.web.gui.SecondPage;
import ${package}.web.gui.InfoPage;

public final class WicketApplicationPrettyUrlConfig {

	private static final Map<String, Class<? extends WebPage>> CONFIG = new HashMap<>();

	static {
		//@formatter:off

		CONFIG.put("/home",   HomePage.class);
		CONFIG.put("/second", SecondPage.class);
		CONFIG.put("/info",   InfoPage.class);

		//@formatter:on
	}

	private WicketApplicationPrettyUrlConfig() {
		// intentionally blank
	}

	public static Map<String, Class<? extends WebPage>> getPageClassByPrettyUrlMap() {
		return CONFIG;
	}
}
