package ${package}.web.gui.page;

import org.apache.wicket.IGenericComponent;
import org.apache.wicket.markup.html.GenericWebPage;
import org.apache.wicket.model.IModel;

@SuppressWarnings({"java:S110"}) // java:S110 -> This class has 7 parents which is greater than 5 authorized.
public abstract class BaseGenericWebPage<T> extends BaseWebPage implements IGenericComponent<T, GenericWebPage<T>> {

	protected BaseGenericWebPage() {
		super();
	}

	protected BaseGenericWebPage(IModel<T> model) {
		super(model);
	}
}
