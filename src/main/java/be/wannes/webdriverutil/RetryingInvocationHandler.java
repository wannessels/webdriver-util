package be.wannes.webdriverutil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.pagefactory.ElementLocator;

public class RetryingInvocationHandler implements InvocationHandler {
	
	@SuppressWarnings("unchecked")
	private static final List<Class<? extends WebDriverException>> RETRYABLE_EXCEPTION_TYPES = Arrays.asList(
			StaleElementReferenceException.class,
			ElementNotVisibleException.class,
			MoveTargetOutOfBoundsException.class,
			NoSuchElementException.class);

	private static final long TIMEOUT_IN_MS = 15000;
	private static final long DELAY_IN_MS = 200;
	
	private ElementLocator locator;
	
	public RetryingInvocationHandler(ElementLocator locator) {
		this.locator = locator;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable, TimeoutException {
		Date timeoutDate = new Date(System.currentTimeMillis() + TIMEOUT_IN_MS);
		Throwable lastThrowable = null;
		while (true) {
			try {
				WebElement webElement = locator.findElement();
				return method.invoke(webElement, args);
			} catch (InvocationTargetException ite) {
				lastThrowable = ite.getCause();
			} catch (Throwable t) {
				lastThrowable = t;
			}
			
			if (isRetryable(lastThrowable) && new Date().before(timeoutDate)) {
				sleep();
				continue;
			} else {
				throw lastThrowable;
			}
		}
	}

	private void sleep()  {
		try {
			Thread.sleep(DELAY_IN_MS);
		} catch (InterruptedException e) {
		}
	}

	
	private boolean isRetryable(Throwable throwable) {
		for (Class<? extends WebDriverException> exceptionType : RETRYABLE_EXCEPTION_TYPES) {
			if (exceptionType.isInstance(throwable)) {
				return true;
			}
		}
		return false;
	}

}
