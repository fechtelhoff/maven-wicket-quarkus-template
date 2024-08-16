package ${package}.cdi;

import org.apache.wicket.Component;
import org.apache.wicket.application.IComponentInstantiationListener;

public class ComponentInjector extends AbstractInjector implements IComponentInstantiationListener {

	@Override
	public void onInstantiation(final Component component) {
		inject(component);
	}
}
