package ${package}.web.cdi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Qualifier;
import org.apache.wicket.injection.IFieldValueFactory;
import org.apache.wicket.proxy.LazyInitProxyFactory;
import io.quarkus.logging.Log;

/**
 * Factory for creating proxies for fields annotated with {@link Inject}.
 * </p></br>
 * Cloned from <a href="https://github.com/dashorst/quarkus-wicket">Quarkus-Wicket on GitHub</a>.
 */
@ApplicationScoped
class ArcAnnotationProxyFactory implements IFieldValueFactory {

	private static final Annotation[] EMPTY_ANNOTATIONS = new Annotation[0];

	/**
	 * Generates the proxy that functions as a stand-in for the (non-serializable)
	 * CDI bean.
	 */
	@Override
	public Object getFieldValue(Field field, Object injectionPoint) {
		if (Log.isDebugEnabled()) {
			Log.debugf("Injecting %s#%s", injectionPoint.getClass().getSimpleName(), field.getName());
		}

		Class<?> beanType = field.getType();
		Annotation[] fieldAnnotations = field.getAnnotations();

		// if there is only one annotation, that is the @Inject annotation, which is not
		// a qualifier, so we can skip it (resulting in an empty array of qualifiers)
		Annotation[] qualifiers = fieldAnnotations.length == 1 ? EMPTY_ANNOTATIONS
			: Arrays.stream(fieldAnnotations)
			.filter(a -> a.annotationType().isAnnotationPresent(Qualifier.class))
			.toArray(Annotation[]::new);

		// the factory has its own caching of proxies, such that only one proxy is created
		return LazyInitProxyFactory.createProxy(beanType, new ArcBeanLocator(beanType, qualifiers));
	}

	/**
	 * Checks if the field is annotated with {@link Inject}.
	 */
	@Override
	public boolean supportsField(Field field) {
		return (field.getAnnotation(Inject.class) != null);
	}
}
