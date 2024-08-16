package ${package}.web.gui;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import ${package}.web.gui.page.BaseWebPage;
import ${package}.web.gui.panel.WicketSettingsPanel;

@SuppressWarnings("java:S110") // java:S110 -> Inheritance tree of classes should not be too deep.
public class InfoPage extends BaseWebPage {

	public InfoPage() {
		super();
	}

	@Override
	public String getPageTitle() {
		return "Info Page";
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new Label("title", Model.of(getPageTitle())));
		add(new WicketSettingsPanel("wicketSettingsPanel"));
	}
}
