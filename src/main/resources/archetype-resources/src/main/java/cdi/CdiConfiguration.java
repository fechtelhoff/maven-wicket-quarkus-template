package ${package}.cdi;

import org.apache.wicket.Application;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.request.cycle.RequestCycleListenerCollection;

public class CdiConfiguration {

	private static final MetaDataKey<CdiConfiguration> CDI_CONFIGURATION_KEY = new MetaDataKey<>() {};

	public void configure(final Application application) {
		if (application.getMetaData(CDI_CONFIGURATION_KEY) != null) {
			throw new IllegalStateException("Cdi already configured for this application");
		}
		application.setMetaData(CDI_CONFIGURATION_KEY, this);
		application.getRequestCycleListeners().add(new RequestCycleListenerCollection());
		application.getComponentInstantiationListeners().add(new ComponentInjector());
	}

	public static CdiConfiguration get(Application application) {
		return application.getMetaData(CDI_CONFIGURATION_KEY);
	}
}
