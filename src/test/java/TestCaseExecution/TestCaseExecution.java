package TestCaseExecution;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import csvFile.CSVFileWriter;

public class TestCaseExecution {
	
	
	private static WebDriver driver;
	public static Properties prop = new Properties();
	 
	 public static WebDriver getDriver() {
	        return driver;
	    }
	
	
	@BeforeTest
	public void beforeTest(ITestContext context) throws IOException
	{
		FileReader fileReader = new FileReader(System.getProperty("user.dir")+"\\src\\test\\resources\\config\\locators.properties");
		prop.load(fileReader);
		String URL = prop.getProperty("url");
		if("chrome".equalsIgnoreCase(prop.getProperty("browser")))
		{
			
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.get(prop.getProperty("url"));
		}
		else if("firefox".equalsIgnoreCase(prop.getProperty("browser")))
		{
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			driver.get(prop.getProperty("url"));
		}
		
		
		driver.manage().window().maximize();		
	}
	
	@AfterTest
	public void afterTest()
	{
		driver.quit();
	}
	
	@Test
	public void TestCase_FilterPriceRange() throws InterruptedException, NumberFormatException
	{

		WebElement SearchItem = driver.findElement(By.xpath(prop.getProperty("TopSearch_text")));
		SearchItem.click();
		
		// pass the keyword in Search field
		SearchItem.sendKeys("Wireless headphone");
		WebElement Go_btn = driver.findElement(By.xpath(prop.getProperty("click_Search_button")));
		Go_btn.click();
		
        // Using the Max Price toggle on price slide filter
		WebElement slider = driver.findElement(By.id(prop.getProperty("price_slider_filter")));  
		WebElement maxPriceonSlider = driver.findElement(By.xpath(prop.getProperty("Max_btn_on_price_slider_filter")));
		String price = maxPriceonSlider.getText();
		
		String maxPrice = price.replaceAll("[^\\d]", "");
        int maxSliderValue = 189; // Slider's maximum value
        int targetPrice = 150; // Desired price
        int maxpriceValue = Integer.parseInt(maxPrice);
        // Calculate the corresponding slider value for the target price
        int targetSliderValue = (int) ((targetPrice / (double) maxpriceValue) * maxSliderValue);

        String script = "arguments[0].value='" + targetSliderValue + "'; " +
                "arguments[0].dispatchEvent(new Event('input')); " +
                "arguments[0].dispatchEvent(new Event('change'));";
       ((JavascriptExecutor) driver).executeScript(script, slider);
       Thread.sleep(5000);		
	}
	
	
	@Test
	public void TestCase_SearchItem() throws InterruptedException, IOException
	{
		// Get the product list
		List<WebElement> ItemList = driver.findElements(By.xpath(prop.getProperty("ProductList")));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		for(int i=1; i<= ItemList.size(); i++)
		{
			// Get the Title of each product widget
	     WebElement eachItem_Title = driver.findElement(By.xpath(prop.getProperty("ProductList")+ "[" + i + "]" + prop.getProperty("ProductTitle")));
	     String eachItemTitle = eachItem_Title.getText();
	     
	       // Get the Review Count of each product widget
	     Thread.sleep(2000);
	     WebElement getReviewCount = driver.findElement(By.xpath(prop.getProperty("ProductList")+ "["+ i +"]" + prop.getProperty("ProductReview")));
	     String get_ReviewCount = getReviewCount.getText();
	     Thread.sleep(2000);
	     WebElement ProductRating = driver.findElement(By.xpath(prop.getProperty("ProductList")+ "["+ i +"]" + prop.getProperty("ProductRating")));
	     String get_ProductRating = ProductRating.getAttribute("aria-label");
	     Thread.sleep(2000);
	     WebElement ProductPrice = driver.findElement(By.xpath(prop.getProperty("ProductList")+ "["+ i +"]" + prop.getProperty("ProductPrice")));
	     String get_ProductPrice = ProductPrice.getText();
	     
	     CSVFileWriter.WriteCSVFileWriter(eachItemTitle, get_ReviewCount,get_ProductRating,get_ProductPrice);
	     
	     System.out.println("-----------> Product item  " + i + "  Executed <--------------");
		}
		
	}

}