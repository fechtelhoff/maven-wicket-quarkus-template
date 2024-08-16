package ${package}.web.gui.panel;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import de.agilecoders.wicket.core.Bootstrap;

public class WicketSettingsPanel extends Panel {

	public WicketSettingsPanel(final String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new Label("wicketVersion", getApplication().getFrameworkSettings().getVersion()));
		add(new Label("bootstrapVersion", Bootstrap.getSettings().getVersion()));
		add(new Label("bootstrapTheme", Bootstrap.getSettings().getActiveThemeProvider().getActiveTheme().name()));
	}
}
