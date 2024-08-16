package ${package}.cdi;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import jakarta.enterprise.context.spi.CreationalContext;
import jakarta.enterprise.inject.spi.InjectionTarget;
import jakarta.inject.Inject;
import io.quarkus.arc.impl.CreationalContextImpl;

public class NonContextual<T> {

	private final InjectionTarget<T> injectionTarget;

	public static <T> NonContextual<T> of(final T t) {
		return (NonContextual<T>) NonContextual.of(t.getClass());
	}

	public static <T> NonContextual<T> of(final Class<T> clazz) {
		return new NonContextual<>(clazz);
	}

	private NonContextual(final Class<? extends T> clazz) {
		// mein InjectionTarget, ist die Klasse die reingegeben wird.
		// Auf einen Cache der NonContextual Typen könnte man zurückgreifen. Ich probiers erstmal ohne. So minimalistisch wie möglich
		final List<Field> injectableFields = Arrays.stream(clazz.getDeclaredFields())
			.filter(field -> field.isAnnotationPresent(Inject.class))
			.toList();
		injectionTarget = new WicketQuarkusInjectionTarget<>(injectableFields);
	}

	public void postConstruct(final T instance) {
		throw new UnsupportedOperationException();
	}

	public void inject(final T instance) {
		// Hier muss in die gegebene Instanz injected werden
		// Erzeuge den CreationalContext auf die Weise, wie es die Wicket-CDI Integration und der WeldBeanManagerImpl getan hätten.
		final CreationalContext<T> creationalContext = new CreationalContextImpl<>(null);
		injectionTarget.inject(instance, creationalContext);
	}

	public void preDestroy(final T instance) {
		// Intentionally blank
	}
}
