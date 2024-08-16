package ${package}.web.gui;

import java.util.function.Supplier;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ${package}.web.WicketTestApplication;

class PageTest {

	private WicketTester tester;

	private enum PageToBeTested {
		HOME(HomePage::new),
		SECOND(SecondPage::new),
		INFO(InfoPage::new),
		/**/;

		private final Supplier<WebPage> supplier;

		PageToBeTested(final Supplier<WebPage> supplier) {
			this.supplier = supplier;
		}

		public Supplier<WebPage> getSupplier() {
			return supplier;
		}
	}

	@BeforeEach
	void setUp() {
		tester = new WicketTester(new WicketTestApplication());
	}

	@ParameterizedTest
	@EnumSource(PageToBeTested.class)
	void pageRendersSuccessfully(final PageToBeTested pageToBeTested) {
		final WebPage page  = pageToBeTested.getSupplier().get();

		//start and render the test page
		tester.startPage(page);

		//assert rendered page class
		tester.assertRenderedPage(page.getClass());
	}
}
