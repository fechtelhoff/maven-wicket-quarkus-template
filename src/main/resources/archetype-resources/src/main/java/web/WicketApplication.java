package ${package}.web;

import java.util.Arrays;
import java.util.logging.Logger;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Inject;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import de.agilecoders.wicket.core.settings.CookieThemeProvider;
import de.agilecoders.wicket.core.settings.IBootstrapSettings;
import de.agilecoders.wicket.core.settings.ThemeProvider;
import de.agilecoders.wicket.themes.markup.html.bootswatch.BootswatchTheme;
import de.agilecoders.wicket.themes.markup.html.bootswatch.BootswatchThemeProvider;
import de.agilecoders.wicket.webjars.WicketWebjars;
import de.agilecoders.wicket.webjars.settings.WebjarsSettings;
import ${package}.cdi.CdiConfiguration;
import ${package}.web.gui.HomePage;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class WicketApplication extends WebApplication {

	@Override
	public Class<? extends WebPage> getHomePage() {
		return HomePage.class;
	}

	@Override
	protected void init() {
		super.init();

		initializeCdi();
//		initializeComponentInstantiationListeners();
		initializeCsp();
		initializeBootstrap();
		initializeWebJars();
	}

	/**
	 * Code Snippet from <a href="https://github.com/mattdru/wicket-quarkus-cdi">GitHub - mattdru/wicket-quarkus-cdi: CDI Quarkus Integration for Apache Wicket</a>
	 */
	private void initializeCdi() {
		// Achtung: CdiConfiguration aus eigenem Package (Implementierung von Matthias Drummer) und nicht die von Wicket.
		new CdiConfiguration().configure(this);
	}

	 /**
	 *
	 * Code Snippet from <a href="https://github.com/brunoborges/wicket-with-quarkus">GitHub - brunoborges/wicket-with-quarkus</a>
	 */
	@SuppressWarnings("unused")
	private void initializeComponentInstantiationListeners() {
		getComponentInstantiationListeners().add(
			component -> Arrays.stream(component.getClass().getDeclaredFields()).forEach(
				x -> {
					if (x.isAnnotationPresent(Inject.class)) {
						final var c = CDI.current().select(x.getType()).get();
						final boolean canAccess = x.canAccess(component);
						x.setAccessible(true);
						try {
							x.set(component, c);
							Logger.getLogger("WicketApplication#init")
								.info("injecting " + component.getClass().getSimpleName() + "#" + x.getName() + "::" + x.getType().getSimpleName());
						} catch (final IllegalAccessException exception) {
							throw new RuntimeException(exception);
						}
						x.setAccessible(canAccess);
					}
				}
			)
		);
	}

	private void initializeCsp() {
		getCspSettings().blocking().disabled();
	}

	private void initializeBootstrap() {
		final IBootstrapSettings bootstrapSettings = new BootstrapSettings();
		final ThemeProvider themeProvider = new BootswatchThemeProvider(BootswatchTheme.Cerulean);
		bootstrapSettings.setThemeProvider(themeProvider);
		bootstrapSettings.setActiveThemeProvider(new CookieThemeProvider());
		Bootstrap.install(this, bootstrapSettings);
	}

	private void initializeWebJars() {
		final WebjarsSettings settings = new WebjarsSettings();
		settings.useCdnResources(false);
		WicketWebjars.install(this, settings);
	}

	private void mountPages() {
		WicketApplicationPrettyUrlConfig.getPageClassByPrettyUrlMap().forEach(this::mountPage);
	}
}
