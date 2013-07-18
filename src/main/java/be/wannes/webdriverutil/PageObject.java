package be.wannes.webdriverutil;

import static com.google.common.base.Predicates.and;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;

public class PageObject {

	private static final int TIME_OUT_IN_SECONDS = 15;
	private RetryingWebDriverDecorator webDriver;

	public PageObject(WebDriver webDriver) {
		FieldDecorator decorator = new RetryingFieldDecorator(new DefaultElementLocatorFactory(webDriver));
		PageFactory.initElements(decorator, this);
		this.webDriver = decorateWebDriverIfNeeded(webDriver);
		waitForJQuery();
		waitForBootstrapModal();
	}

	private RetryingWebDriverDecorator decorateWebDriverIfNeeded(WebDriver webDriver) {
		if (webDriver instanceof RetryingWebDriverDecorator) {
			return (RetryingWebDriverDecorator) webDriver;
		} else {
			return new RetryingWebDriverDecorator(webDriver);
		}
	}
	
	public RetryingWebDriverDecorator getDriver() {
		return webDriver;
	}
	
	public void waitForJQuery() {
		Predicate<WebDriver> jQueryIsInactive = and(JQueryAjax.isInactive(), JQueryAnimation.isInactive());
		waitUntil(jQueryIsInactive);
	}
	
	public void waitForBootstrapModal() {
		waitUntil(BootstrapModalAnimation.isInactive());
		BootstrapModalAnimation.registerEventHandler(webDriver.getWrappedDriver());
	}
	
	protected void waitUntil(Predicate<WebDriver> predicate) {
		new WebDriverWait(getDriver().getWrappedDriver(), TIME_OUT_IN_SECONDS).until(predicate);
	}
	
	
}
