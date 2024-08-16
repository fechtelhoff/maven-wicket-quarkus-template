package ${package}.web.gui;

import java.time.LocalDateTime;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import ${package}.web.gui.page.BaseGenericWebPage;

@SuppressWarnings("java:S110") // java:S110 -> Inheritance tree of classes should not be too deep
public class SecondPage extends BaseGenericWebPage<SecondPageMO> {

	public SecondPage() {
		super();
		setDefaultModel(Model.of(new SecondPageMO(LocalDateTime.now())));
	}

	@Override
	public String getPageTitle() {
		return "Second Page";
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new Label("title", Model.of(getPageTitle())));
		add(new Label("label", "Start here ..."));
		add(new Label("timestamp", getModelObject().getTimestampAsString()));
	}
}
