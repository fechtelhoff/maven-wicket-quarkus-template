package ${package}.web;

import java.nio.charset.StandardCharsets;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import ${package}.web.cdi.ArcInjector;
import ${package}.web.gui.HomePage;
import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import de.agilecoders.wicket.core.settings.CookieThemeProvider;
import de.agilecoders.wicket.core.settings.IBootstrapSettings;
import de.agilecoders.wicket.core.settings.ThemeProvider;
import de.agilecoders.wicket.themes.markup.html.bootswatch.BootswatchTheme;
import de.agilecoders.wicket.themes.markup.html.bootswatch.BootswatchThemeProvider;
import de.agilecoders.wicket.webjars.WicketWebjars;
import de.agilecoders.wicket.webjars.settings.WebjarsSettings;

@Singleton
public class WicketApplication extends WebApplication {

	@Inject
	ArcInjector injector;

	@Override
	public Class<? extends WebPage> getHomePage() {
		return HomePage.class;
	}

	@Override
	protected void init() {
		super.init();

		initializeUtf8();
		initializeCsp();
		initializeCdi();
		initializeBootstrap();
		initializeWebJars();

		mountPages();
	}

	private void initializeUtf8() {
		getMarkupSettings().setDefaultMarkupEncoding(StandardCharsets.UTF_8.name());
		getRequestCycleSettings().setResponseRequestEncoding(StandardCharsets.UTF_8.name());
	}

	private void initializeCsp() {
		getCspSettings().blocking().disabled();
	}

	private void initializeCdi() {
		// ArC CDI configuration.
		injector.bind(this);
		getBehaviorInstantiationListeners().add(injector);
		getComponentInstantiationListeners().add(injector);
		getSessionListeners().add(injector);
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
