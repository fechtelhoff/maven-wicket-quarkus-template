package ${package}.web.cdi;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import jakarta.enterprise.context.spi.CreationalContext;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.enterprise.inject.spi.InjectionTarget;
import io.quarkus.arc.Arc;
import io.quarkus.arc.ArcContainer;
import io.quarkus.arc.InstanceHandle;

/**
 * Injiziert Inject-Felder mit entsprechenden Proxies /Instanzen mithilfe der Quarkus-BeanManager-Implementierung Arc
 * @param <T>
 */
public class QuarkusFieldInjectionTarget<T> implements InjectionTarget<T> {

	private final List<Field> injectableFields;

	public QuarkusFieldInjectionTarget(final List<Field> injectableFields) {
		this.injectableFields = injectableFields;
	}

	@Override
	public T produce(final CreationalContext<T> ctx) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void dispose(final T instance) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<InjectionPoint> getInjectionPoints() {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings({"deprecation", "java:S3011", "java:S112"})
	// java:S3011: "Reflection should not be used to increase accessibility of classes, methods, or fields" -> in dem Fall ist es notwendig.
	// "deprecation": AccessibleObject#isAccessible()-Aufruf: Der prüft nur, ob Access-Checks eingeschaltet sind. #setAccessible() setzt ebenjene Property.
	// Dass nicht beide Methoden gleichermaßen deprecated sind, ist inkonsequent und die dargebotene Alternative #canAccess() hat außerdem einen abweichenden Zweck.
	// java:S112 -> Generic exceptions should never be thrown
	@Override
	public void inject(final T instance, final CreationalContext<T> ctx) {
		final ArcContainer container = Arc.container();
		injectableFields.forEach(injectableField -> {
			final boolean canAccess = injectableField.isAccessible();
			injectableField.setAccessible(true);
			try(InstanceHandle<?> handle = container.instance(injectableField.getType())) {
				final Object fieldValue = handle.get();

				injectableField.set(instance, fieldValue);
			} catch (final IllegalAccessException exception) {
				throw new RuntimeException("Unhandled exception occurred.", exception);
			}
			injectableField.setAccessible(canAccess);
		});
	}

	@Override
	public void postConstruct(final T instance) {
		// macht nix. Keine Exception, da der Aufruf frameworkseitig ungesteuert kommen kann -> kein Fehler
	}

	@Override
	public void preDestroy(final T instance) {
		// macht nix. Keine Exception, da der Aufruf frameworkseitig ungesteuert kommen kann -> kein Fehler
	}
}