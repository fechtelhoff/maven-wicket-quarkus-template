package ${package}.web.cdi;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import jakarta.enterprise.context.spi.CreationalContext;
import jakarta.enterprise.inject.spi.InjectionTarget;
import jakarta.inject.Inject;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.wicket.util.collections.ClassMetaCache;
import io.quarkus.arc.impl.CreationalContextImpl;

/**
 * Variante von wicket-cdi-Contextual zum "Dekorieren" von unmanaged beans mit CDI-Funktionalität (v.a. @Inject - Auflösung).
 * Z.Z. wird NUR Field-Injection unterstützt, keine Setter- und Constructor-Injection.
 * @param <T> Typ der Objekte, die dekoriert werden sollen
 */
public class NonContextual<T> {

	private final InjectionTarget<T> injectionTarget;

	/**
	 * Cache zum schnelleren Zugriff auf Noncontextual-Klassen
	 */
	@SuppressWarnings("java:S3077") // ClassMetaCache ist m.E. threadsafe aufgrund der ConcurrentHashMap-Implementierung und der Put-Synchronisierung
	private static final ClassMetaCache<NonContextual<?>> classCacheNoncontextual = new ClassMetaCache<>();

	@SuppressWarnings("unchecked")
	public static <T> NonContextual<T> of(T t) {
		return (NonContextual<T>)of(t.getClass());
	}

	public static <T> NonContextual<T> of(Class<? extends T> clazz)
	{
		@SuppressWarnings("unchecked")
		NonContextual<T> nc = (NonContextual<T>)classCacheNoncontextual.get(clazz);

		if (nc == null)
		{
			nc = new NonContextual<>(clazz);
			classCacheNoncontextual.put(clazz, nc);
		}
		return nc;
	}

	private NonContextual(final Class<? extends T> clazz) {
		final List<Field> injectableFields = Arrays.stream(FieldUtils.getAllFields(clazz))
			.filter(field -> field.isAnnotationPresent(Inject.class))
			.toList();
		injectionTarget = new QuarkusFieldInjectionTarget<>(injectableFields);
	}

	public void inject(final T instance) {
		// Hier muss in die gegebene Instanz injected werden
		// Erzeuge den CreationalContext auf die Weise, wie es die Wicket-CDI Integration und der WeldBeanManagerImpl getan hätten.
		final CreationalContext<T> creationalContext = new CreationalContextImpl<>(null);
		injectionTarget.inject(instance, creationalContext);
	}
}