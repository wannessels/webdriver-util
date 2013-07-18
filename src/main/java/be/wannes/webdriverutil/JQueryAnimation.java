package be.wannes.webdriverutil;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public class JQueryAnimation implements Predicate<WebDriver> {
	
	private JQueryAnimation() {
	}
	
	public boolean apply(WebDriver webDriver) {
		boolean animationActive = (Boolean)((JavascriptExecutor)webDriver).executeScript("return $(':animated').length > 0");
		System.out.println("animationActive: " + animationActive);
		return animationActive;
	}
	
	public static Predicate<WebDriver> isActive() {
		return new JQueryAnimation();
	}
	
	public static Predicate<WebDriver> isInactive() {
		return Predicates.not(isActive());
	}
	
	@Override
	public String toString() {
		return getClass().getName() + ".isActive";
	}
	
}
