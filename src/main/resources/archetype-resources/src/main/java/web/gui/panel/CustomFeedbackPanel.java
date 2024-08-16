package ${package}.web.gui.panel;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * Ein modifiziertes Feedback Panel mit Bootstrap Labels.
 * <a href="https://stackoverflow.com/questions/16129558/different-css-on-wickets-feedbackpanel">Taken from</a>
 */
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class CustomFeedbackPanel extends FeedbackPanel {

	public CustomFeedbackPanel(final String id) {
		super(id);
		applyDefaultConfig();
	}

	public CustomFeedbackPanel(final String id, final IFeedbackMessageFilter filter) {
		super(id, filter);
		applyDefaultConfig();
	}

	private void applyDefaultConfig() {
		super.setEscapeModelStrings(false);
	}

	@Override
	protected String getCSSClass(final FeedbackMessage message) {
		return switch (message.getLevel()) {
			case FeedbackMessage.SUCCESS -> "alert alert-success";
			case FeedbackMessage.INFO -> "alert alert-info";
			case FeedbackMessage.ERROR -> "alert alert-danger";
			case FeedbackMessage.WARNING -> "alert alert-warning";
			default -> "alert";
		};
	}
}
