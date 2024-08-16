package ${package}.cdi;

public abstract class AbstractInjector {

	protected AbstractInjector() {
		// Intentionally blank
	}

	protected <T> void inject(T instance) {
		NonContextual.of(instance).inject(instance);
	}
}
