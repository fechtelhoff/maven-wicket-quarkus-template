package ${package}.web.gui.panel;

import java.time.LocalDate;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class FooterPanel extends Panel {

	public FooterPanel(final String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		final int year = LocalDate.now().getYear();
		final String copyright = String.format("Copyright &copy; %d by Ulrich Fechtelhoff", year);

		add(new Label("copyright", Model.of(copyright)).setEscapeModelStrings(false));
	}
}
