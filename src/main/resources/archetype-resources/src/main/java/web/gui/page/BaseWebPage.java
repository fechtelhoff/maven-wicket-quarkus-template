package ${package}.web.gui.page;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import ${package}.web.gui.panel.FooterPanel;
import ${package}.web.gui.panel.HeaderPanel;


public abstract class BaseWebPage extends WebPage implements PageTitled {

	protected BaseWebPage() {
		add(new Label("pageTitle", Model.of(getPageTitle())));
	}

	protected BaseWebPage(final IModel<?> model) {
		super(model);
	}

	protected BaseWebPage(final PageParameters parameters) {
		super(parameters);
	}

	@Override
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response);
		HtmlHeaderSupport.renderHead(response);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new HeaderPanel("header"));
		add(new FooterPanel("footer"));
	}
}
