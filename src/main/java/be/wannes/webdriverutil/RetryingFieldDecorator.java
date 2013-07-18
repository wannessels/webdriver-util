package be.wannes.webdriverutil;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

public class RetryingFieldDecorator extends DefaultFieldDecorator{
	
	private RetryingWebElementDecoratorFactory retryingWebElementDecoratorFactory;

	public RetryingFieldDecorator(ElementLocatorFactory factory) {
		super(factory);
		retryingWebElementDecoratorFactory = new RetryingWebElementDecoratorFactory();
	}
	
	@Override
	protected WebElement proxyForLocator(ClassLoader loader,ElementLocator locator) {
		return retryingWebElementDecoratorFactory.proxyForLocator(loader, locator);
	}

}
