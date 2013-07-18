package be.wannes.webdriverutil;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public class BootstrapModalAnimation implements Predicate<WebDriver>{

	public boolean apply(WebDriver webDriver) {
		boolean animationActive = (Boolean)((JavascriptExecutor)webDriver).executeScript("return typeof bootstrapModalAnimations != 'undefined' && bootstrapModalAnimations > 0");
		System.out.println("bootstrapModalAnimations: " + animationActive);
		return animationActive;
	}
	
	public static Predicate<WebDriver> isActive() {
		return new BootstrapModalAnimation();
	}
	
	public static Predicate<WebDriver> isInactive() {
		return Predicates.not(isActive());
	}
	
	@Override
	public String toString() {
		return getClass().getName() + ".isActive";
	}
	
	static void registerEventHandler(WebDriver webDriver) {
		InputStream jsStream = BootstrapModalAnimation.class.getResourceAsStream("/bootstrapModalEventHandler.js");
		assert jsStream != null;
		StringWriter jsStringWriter = new StringWriter();
		try {
			IOUtils.copy(jsStream, jsStringWriter,"UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		((JavascriptExecutor)webDriver).executeScript(jsStringWriter.toString());
	}
	
	

}
