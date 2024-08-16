package ${package}.web.gui.panel;

import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.INavbarComponent;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarComponents;
import de.agilecoders.wicket.core.markup.html.bootstrap.utilities.BackgroundColorBehavior;
import ${package}.web.gui.NavbarEntries;
import ${package}.web.gui.common.LogoResourceReference;

public class HeaderPanel extends Panel {

	public HeaderPanel(final String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		final Navbar navbar = new Navbar("navbar", Model.of());
		navbar.setInverted(true);
		navbar.setBrandName(Model.of("Test Project"));
		navbar.setBrandImage(LogoResourceReference.INSTANCE, Model.of("Project Logo"));
		navbar.setBackgroundColor(BackgroundColorBehavior.Color.Primary);
		navbar.addComponents(getNavbarEntries());
		add(navbar);
	}

	private List<INavbarComponent> getNavbarEntries() {
		return Arrays.stream(NavbarEntries.values())
			.map(entry ->
				NavbarComponents.transform(
					entry.getPosition(),
					new NavbarButton<Void>(entry.getPage(), Model.of(entry.getLabel()))
				)
			)
			.flatMap(List::stream)
			.toList();
	}
}
