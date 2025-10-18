package ${package}.web.cdi;

import java.io.Serial;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import org.apache.wicket.proxy.IProxyTargetLocator;

/**
 * Locates CDI beans upon deserialization of the proxy that stands in for the CDI bean.
 * </p></br>
 * Cloned from <a href="https://github.com/dashorst/quarkus-wicket">Quarkus-Wicket on GitHub</a>.
 */
class ArcBeanLocator implements IProxyTargetLocator {

	@Serial
	private static final long serialVersionUID = 1L;

	private final Class<?> beanClass;

	private final Annotation[] qualifiers;

	private transient String stringValue;

	ArcBeanLocator(Class<?> beanClass, Annotation[] qualifiers) {
		this.beanClass = beanClass;
		this.qualifiers = qualifiers;
	}

	Class<?> beanClass() {
		return beanClass;
	}

	Annotation[] qualifiers() {
		return qualifiers;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T locateProxyTarget() {
		return (T) ArcInjector.getInstance().getBean(this);
	}

	@Override
	public final String toString() {
		if (stringValue == null) {
			stringValue = "ArcBeanLocator{" +
				"beanClass=" + beanClass + (qualifiers.length == 0 ? ""
				: ", qualifiers=" + Arrays.toString(qualifiers))
				+
				'}';
		}
		return stringValue;
	}
}
