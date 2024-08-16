package ${package}.web.gui.common;

import org.apache.wicket.request.resource.CssResourceReference;

public class LogoResourceReference extends CssResourceReference {

	public static final LogoResourceReference INSTANCE = new LogoResourceReference();

	public LogoResourceReference() {
		super(LogoResourceReference.class, "logo.png");
	}
}
