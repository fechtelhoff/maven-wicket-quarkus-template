package ${package}.web.gui;

import org.apache.wicket.markup.html.WebPage;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar;

public enum NavbarEntries {

	HOME("Home", HomePage.class, Navbar.ComponentPosition.LEFT),
	SECOND("Second", SecondPage.class, Navbar.ComponentPosition.LEFT),
	INFO("Info", InfoPage.class, Navbar.ComponentPosition.RIGHT),
	/**/;

	private final String label;
	private final Class<? extends WebPage> page;
	private final Navbar.ComponentPosition position;

	NavbarEntries(
		final String label,
		final Class<? extends WebPage> page,
		final Navbar.ComponentPosition position
	) {
		this.label = label;
		this.page = page;
		this.position = position;
	}

	public String getLabel() {
		return label;
	}

	public Class<? extends WebPage> getPage() {
		return page;
	}

	public Navbar.ComponentPosition getPosition() {
		return position;
	}
}
