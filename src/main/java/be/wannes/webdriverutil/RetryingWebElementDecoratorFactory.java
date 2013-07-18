package be.wannes.webdriverutil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

public class RetryingWebElementDecoratorFactory {
	
	public WebElement proxyForLocator(ClassLoader loader,ElementLocator locator) {
		InvocationHandler handler = new RetryingInvocationHandler(locator);

	    WebElement proxy;
	    proxy = (WebElement) Proxy.newProxyInstance(
	        loader, new Class[] {WebElement.class, WrapsElement.class, Locatable.class}, handler);
	    return proxy;
	}
}
