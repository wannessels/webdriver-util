package be.wannes.webdriverutil;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public class JQueryAjax implements Predicate<WebDriver>{
	
	private JQueryAjax() {
	}
	
	public boolean apply(WebDriver webDriver) {
		boolean ajaxActive = (Boolean) ((JavascriptExecutor)webDriver).executeScript("return jQuery.active != 0");
		System.out.println("ajaxActive: " + ajaxActive);
		return ajaxActive;
	}
	
	public static Predicate<WebDriver> isActive() {
		return new JQueryAjax();
	}
	
	public static Predicate<WebDriver> isInactive() {
		return Predicates.not(isActive());
	}
	
	@Override
	public String toString() {
		return getClass().getName() + ".isActive";
	}
}
