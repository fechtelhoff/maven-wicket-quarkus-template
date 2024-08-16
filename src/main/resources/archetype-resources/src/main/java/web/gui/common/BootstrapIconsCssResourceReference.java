package ${package}.web.gui.common;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.request.resource.ResourceReference;
import de.agilecoders.wicket.webjars.request.resource.WebjarsCssResourceReference;

/**
 * Stellt eine Referenz auf die Bootstrap Icons zur Verfügung.<br/>
 * Hierzu muss in Maven die entsprechende Dependency eingebunden werden.
 * <p/>
 * <pre>
 * <dependency>
 *     <groupId>org.webjars.npm</groupId>
 *     <artifactId>bootstrap-icons</artifactId>
 *     <version>1.11.1</version>
 * </dependency>
 * </pre>
 * <p/>
 * Die Bootstrap Icons können unter <a href="https://icons.getbootstrap.com">https://icons.getbootstrap.com</a> angezeigt und durchsucht werden.
 */
public class BootstrapIconsCssResourceReference extends WebjarsCssResourceReference {

	public static final BootstrapIconsCssResourceReference INSTANCE = new BootstrapIconsCssResourceReference();

	private BootstrapIconsCssResourceReference() {
		super("bootstrap-icons/current/font/bootstrap-icons.css");
	}

	@SuppressWarnings("SameReturnValue")
	public static ResourceReference getInstance() {
		return INSTANCE;
	}

	public static HeaderItem asHeaderItem() {
		return CssHeaderItem.forReference(INSTANCE);
	}
}
