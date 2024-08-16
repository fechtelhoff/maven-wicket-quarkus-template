package ${package}.web;

import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import org.apache.wicket.protocol.http.WicketFilter;

@WebFilter(value = "/*", initParams = {
	@WebInitParam(name = "applicationClassName", value = "${package}.web.WicketApplication")})
public class WicketApplicationFilter extends WicketFilter {

}
