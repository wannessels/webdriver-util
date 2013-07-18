package be.wannes.webdriverutil;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RetryingWebDriverDecorator implements WebDriver {
	
	private WebDriver webDriver;
	private RetryingWebElementDecoratorFactory retryingWebElementDecoratorFactory;

	public RetryingWebDriverDecorator(WebDriver original) {
		this.webDriver = original;
		this.retryingWebElementDecoratorFactory = new RetryingWebElementDecoratorFactory();
	}

	public void get(String url) {
		webDriver.get(url);
	}

	public String getCurrentUrl() {
		return webDriver.getCurrentUrl();
	}

	public String getTitle() {
		return webDriver.getTitle();
	}

	public List<WebElement> findElements(By by) {
		return webDriver.findElements(by);
	}

	public WebElement findElement(By by) {
		ByElementLocator locator = new ByElementLocator(by, webDriver);
		ClassLoader classLoader = getClass().getClassLoader();
		return retryingWebElementDecoratorFactory.proxyForLocator(classLoader, locator);
	}

	public String getPageSource() {
		return webDriver.getPageSource();
	}

	public void close() {
		webDriver.close();
	}

	public void quit() {
		webDriver.quit();
	}

	public Set<String> getWindowHandles() {
		return webDriver.getWindowHandles();
	}

	public String getWindowHandle() {
		return webDriver.getWindowHandle();
	}

	public TargetLocator switchTo() {
		return webDriver.switchTo();
	}

	public Navigation navigate() {
		return webDriver.navigate();
	}

	public Options manage() {
		return webDriver.manage();
	}
	
	public WebDriver getWrappedDriver() {
		return webDriver;
	}

}
