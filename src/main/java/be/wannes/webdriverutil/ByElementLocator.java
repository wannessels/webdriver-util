package be.wannes.webdriverutil;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

public class ByElementLocator implements ElementLocator {
	
	private By by;
	private SearchContext searchContext;

	public ByElementLocator(By by, SearchContext searchContext) {
		this.by = by;
		this.searchContext = searchContext;
	}

	public WebElement findElement() {
		return searchContext.findElement(by);
	}

	public List<WebElement> findElements() {
		return searchContext.findElements(by);
	}

}
