package CommonUtility;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import TestCaseExecution.TestCaseExecution;

public class CommonMethod {
	
	static WebDriver driver = TestCaseExecution.getDriver();
	
	public static void ExplicitWait(WebElement element)
	{
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.invisibilityOf(element));
		
		
	}

}