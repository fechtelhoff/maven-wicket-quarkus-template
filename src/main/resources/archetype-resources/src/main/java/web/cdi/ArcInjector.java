package ${package}.web.cdi;

import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Inject;
import org.apache.wicket.Component;
import org.apache.wicket.IBehaviorInstantiationListener;
import org.apache.wicket.ISessionListener;
import org.apache.wicket.Session;
import org.apache.wicket.application.IComponentInstantiationListener;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.injection.Injector;
import io.quarkus.logging.Log;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

/**
 * Injects ARC managed CDI beans into Wicket components, behaviors and sessions.
 *
 * <h2>Injecting CDI beans</h2>
 * <p>
 * The integration automatically injects CDI beans into Wicket components,
 * behaviors and sessions. You can use {@link Inject} to mark fields in your
 * components, behaviors and sessions that should be injected with CDI beans.
 * It is possible to use CDI qualifiers as well.
 * <p>
 * Other Wicket objects (such as models) have no lifecycle listeners, and are
 * not automatically injected. You can use
 * {@code ArcInjector.getInstance().inject(object)} for those occasions if
 * necessary.
 * </p></br>
 * Cloned from <a href="https://github.com/dashorst/quarkus-wicket">Quarkus-Wicket on GitHub</a>.
 */
@ApplicationScoped
public class ArcInjector extends Injector implements IComponentInstantiationListener, IBehaviorInstantiationListener, ISessionListener {

	/**
	 * Static reference to use in the {@link ArcBeanLocator proxies}
	 */
	private static ArcInjector self;

	@Inject
	ArcAnnotationProxyFactory fieldValueFactory;

	@Inject
	@Any
	Instance<Object> allBeans;

	/**
	 * Cache for storing beans to avoid repeated lookups in CDI.
	 */
	private ConcurrentHashMap<String, Object> lookupCache;

	/**
	 * Returns the singleton instance of the ArcInjector, allows you to use CDI/ARC
	 * injection in other places than components, behaviors and sessions, such as
	 * models.
	 */
	public static ArcInjector getInstance() {
		return Objects.requireNonNull(self);
	}

	/**
	 * Injects CDI beans into the fields of the {@code wicketObject} that are
	 * annotated with {@code @Inject}, this also supports CDI qualifiers.
	 * <p>
	 * This injector is necessary to bridge the unmanaged nature of Wicket concepts
	 * with the managed nature of CDI beans. For example whenever you use
	 * {@code new MyPage()} this creates an object that is unknown to CDI, and
	 * injection would not occur. So we need this to get CDI bean injection to work
	 * with Wicket components, behaviors etc.
	 *
	 * @param wicketObject the object to inject CDI beans into
	 */
	@Override
	public void inject(Object wicketObject) {
		inject(wicketObject, fieldValueFactory);
	}

	/**
	 * Initializer for the injector.
	 *
	 * @param event dummy parameter
	 */
	@SuppressWarnings("java:S2696")
	// Instance methods should not write to "static" fields
	// java:S2696 .. ist hier kein Problem, da ApplicationScoped und initialize/teardown nur einmal aufgerufen werden sollte
	void initialize(@Observes StartupEvent event) {
		lookupCache = new ConcurrentHashMap<>();
		self = this;

		Log.info("ArcComponentInjector initialized");
	}

	/**
	 * Tears down the injector, unregisters the resources it retains.
	 *
	 * @param event dummy parameter
	 */
	@SuppressWarnings("java:S2696")
	// Instance methods should not write to "static" fields
	// java:S2696 .. ist hier kein Problem, da ApplicationScoped und initialize/teardown nur einmal aufgerufen werden sollte
	void teardown(@Observes ShutdownEvent event) {
		lookupCache = null;
		self = null;

		Log.info("ArcComponentInjector destroyed");
	}

	@SuppressWarnings("unused")
	Object getBean(Class<?> type, Annotation... annotations) {
		return getBean(new ArcBeanLocator(type, annotations));
	}

	Object getBean(ArcBeanLocator beanLocator) {
		String key = beanLocator.toString();

		if (Log.isDebugEnabled()) {
			Log.debugf("Getting bean %s", key);
		}

		var bean = lookupCache.get(key);
		if (bean != null) {
			return bean;
		}

		synchronized (lookupCache) {
			if (Log.isDebugEnabled()) {
				Log.debugf("Cache miss for %s", key);
			}
			bean = lookupCache.get(key);
			if (bean != null) {
				return bean;
			}

			bean = CDI.current().select(beanLocator.beanClass(), beanLocator.qualifiers()).get();
			lookupCache.put(key, bean);
			return bean;
		}
	}

	/**
	 * Inject CDI beans into behaviors.
	 */
	@Override
	public void onInstantiation(Behavior behavior) {
		if (Log.isDebugEnabled()) {
			Log.debugf("Instantiating and injecting behavior %s", behavior.getClass().getSimpleName());
		}

		inject(behavior);
	}

	/**
	 * Inject CDI beans into components.
	 */
	@Override
	public void onInstantiation(Component component) {
		if (Log.isDebugEnabled()) {
			Log.debugf(
				"Instantiating and injecting component %s#%s", component.getClass().getSimpleName(),
				component.getId()
			);
		}

		inject(component);
	}

	/**
	 * Inject CDI beans into sessions.
	 */
	@Override
	public void onCreated(Session session) {
		if (Log.isDebugEnabled()) {
			Log.debugf("Session created and injected: %s", session.getId());
		}

		inject(session);
	}
}
