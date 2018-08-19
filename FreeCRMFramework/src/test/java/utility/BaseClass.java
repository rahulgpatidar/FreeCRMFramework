package utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import browserFactory.BrowserFactory;
import dataProvider.ConfigDataProvider;
import dataProvider.ExcelDataProvider;
import de.redsix.pdfcompare.PdfComparator;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.screentaker.ViewportPastingStrategy;

public class BaseClass {

	public static WebDriver driver;
	public static ExcelDataProvider reader;
	
	public static void initialisation() throws Exception 
	{
		driver=BrowserFactory.getBrowser("chrome");
		driver.get(ConfigDataProvider.getApplicationURL());
	}
	
	public static void termination() 
	{
		BrowserFactory.closeBrowser();
	}
	
	public static String captureScreenshot(WebDriver driver,String screenshotName) 
	{
		TakesScreenshot ts=(TakesScreenshot)driver;
		File src=ts.getScreenshotAs(OutputType.FILE);
		String destination=System.getProperty("user.dir")+"/Screenshots/"+screenshotName+System.currentTimeMillis()+".png";
		try 
		{
			FileUtils.copyFile(src, new File(destination));
		} catch (IOException e) 
		{
			e.getMessage();
		}
		return destination;
	}
	
	public static void captureScreenshotAtEndOfTest() throws IOException 
	{
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		
		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
		
	}
	
	public static String captureScreenshotFullPage(WebDriver driver,String screenshotName) 
	{
		Screenshot screenshot = new AShot().shootingStrategy(new ViewportPastingStrategy(500)).takeScreenshot(driver);
		BufferedImage image = screenshot.getImage();
	
		String destination=System.getProperty("user.dir")+"/Screenshots/"+screenshotName+System.currentTimeMillis()+".png";
	
		try 
		{
			ImageIO.write(image, "PNG", new File(destination));
		} 
		catch (IOException e) 
		{	
			e.getMessage();
		}
		return destination;
	}
	
	
	public static void sendkeys(WebDriver driver,WebElement element,int timeout,String value) 
	{
		new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOf(element));
		element.sendKeys(value);
	}

	public static void clickOn(WebDriver driver, WebElement locator, int timeout) {
		new WebDriverWait(driver, timeout).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(locator));
		locator.click();
	}

	public static void staleElementRefExceptionSolution(String xpath) {
		
		for (int i = 0; i <= 3; i++)
		{
			try 
			{
				driver.findElement(By.id(xpath)).click();
				break;
			}
			catch (Exception e) 
			{
				System.out.println(e.getMessage());
			}
		}
	}
	
	public static WebElement waitForElementPresent(WebDriver driver,String xpath,int time)
	{
	  WebElement element = null;
	 
		for (int i = 0; i < time; i++) {
			try 
			{
				element = driver.findElement(By.xpath(xpath));
				break;
			} 
			catch (Exception e) 
			{
				try 
				{
					Thread.sleep(1000);
				} catch (InterruptedException e1) 
				{
					System.out.println("Waiting for element to appear on DOM");
				}
			} 
	}
		return element;
	}
	
	public static WebElement waitForElementPresent(WebElement element,int time)
	{ 
		for (int i = 0; i < time; i++) {
			try 
			{
				new WebDriverWait(driver, time).until(ExpectedConditions.presenceOfElementLocated((By) element));
				break;
			} 
			catch (Exception e) 
			{
				try 
				{
					Thread.sleep(1000);
				} catch (InterruptedException e1) 
				{
					System.out.println("Waiting for element to appear on DOM");
				}
			} 
	}
		return element;
	}
	
	public static void performAction(WebElement element,String action,String value,String type,String id) 
	{
		if(action.equalsIgnoreCase("text")) 
		{
			waitForElementPresent(element, 20);
			element.clear();
			element.sendKeys(value);
		}
		else if(action.equalsIgnoreCase("radio"))
		{
			if (element.isDisplayed() && !element.isSelected()) {
				waitForElementPresent(element, 20);
				element.click();
			}
		}
		else if(action.equalsIgnoreCase("checkbox"))
		{
			if (element.isDisplayed() && !element.isSelected()) {
				waitForElementPresent(element, 20);
				element.click();
			}
		}
		
		else if(action.equalsIgnoreCase("button"))
		{
			if (element.isDisplayed() && element.isEnabled()) {
				waitForElementPresent(element, 20);
				element.click();
			}
		}
		else if(action.equalsIgnoreCase("link"))
		{
			if (element.isDisplayed() && element.isEnabled()) {
				waitForElementPresent(element, 20);
				element.click();
			}
		}
		else if(action.equalsIgnoreCase("hyperlink"))
		{
			if (element.isDisplayed() && element.isEnabled()) {
				waitForElementPresent(element, 20);
				element.click();
			}
		}
		else if(action.equalsIgnoreCase("dropdown"))
		{
			if (element.isDisplayed() && element.isEnabled()) {
				waitForElementPresent(element, 20);
			}
		}
	}
	
	public static void funcFieldValueSet(WebElement element,String action,String value,String type,String id) 
	{
		waitForElementPresent(element, 20);
		performAction(element, action, value, type, id);
	}
	
	public static void selectByIndex(String xpath,int indexValue) 
	{
		WebElement ele=driver.findElement(By.xpath(xpath));
		Select sel=new Select(ele);
		sel.selectByIndex(indexValue);
	}
	
	public static void selectByVisibleText(String xpath,String indexValue) 
	{
		WebElement ele=driver.findElement(By.xpath(xpath));
		Select sel=new Select(ele);
		sel.selectByVisibleText(indexValue);
	}
	
	public static void selectByValue(String xpath,String indexValue) 
	{
		WebElement ele=driver.findElement(By.xpath(xpath));
		Select sel=new Select(ele);
		sel.selectByValue(indexValue);
	}
	public static void getAlloptionFromDropdown() {
		
	}
	
	public static void getBrokenLinks() throws MalformedURLException, IOException 
	{
		List<WebElement>linksList=driver.findElements(By.tagName("a"));
		linksList.addAll(driver.findElements(By.tagName("img")));
		System.out.println("Total number of links and images are---"+linksList.size());
		
		List<WebElement> activeLinks=new ArrayList<>();
		
		for (int i = 0; i < linksList.size(); i++) 
		{
			System.out.println(linksList.get(i).getAttribute("href"));
			if (linksList.get(i).getAttribute("href")!=null && (! linksList.get(i).getAttribute("href").contains("javascript"))) 
			{
				activeLinks.add(linksList.get(i));
			}
		}
		
		for (int i = 0; i < activeLinks.size(); i++) 
		{
			HttpURLConnection connection=(HttpURLConnection)new URL(activeLinks.get(i).getAttribute("href")).openConnection();
			connection.connect();
			String response=connection.getResponseMessage();
			connection.disconnect();
			System.out.println(activeLinks.get(i).getAttribute("href")+"---->"+response);
		}
	}
	
	public static void getAllLinks() 
	{
		List<WebElement> alllinks = driver.findElements(By.tagName("a"));

		for (int i = 0; i < alllinks.size(); i++)
			System.out.println(alllinks.get(i).getText());
	}
	
	public static void randomNumberGeneratorBetweenTwoNumber(int a, int b) {
		// create instance of Random class
		Random random = new Random();

		// Generate random integers in range a to b
		int rand_int1 = random.nextInt(a);
		int rand_int2 = random.nextInt(b);

		// Print random integers
		System.out.println("Random Integers: " + rand_int1);
		System.out.println("Random Integers: " + rand_int2);

	}
	
	public static void randomNumberGenerator() {

		int rand_int1 = ThreadLocalRandom.current().nextInt();
		int rand_int2 = ThreadLocalRandom.current().nextInt();

		// Print random integers
		System.out.println("Random Integers: " + rand_int1);
		System.out.println("Random Integers: " + rand_int2);

	}

	public static void randomBooleanGenerator() {
		// Generate random booleans
		boolean rand_bool1 = ThreadLocalRandom.current().nextBoolean();
		boolean rand_bool2 = ThreadLocalRandom.current().nextBoolean();

		// Print random Booleans
		System.out.println("Random Booleans: " + rand_bool1);
		System.out.println("Random Booleans: " + rand_bool2);
	}

	public static void randomDoubleGenerator() {

		// Generate Random doubles
		double rand_dub1 = ThreadLocalRandom.current().nextDouble();
		double rand_dub2 = ThreadLocalRandom.current().nextDouble();

		// Print random doubles
		System.out.println("Random Doubles: " + rand_dub1);
		System.out.println("Random Doubles: " + rand_dub2);

	}

	public static void randomStringGenerator(int length, boolean useLetters, boolean useNumbers) {

		/*
		 * int length = 10; boolean useLetters = true; boolean useNumbers = false;
		 */
		
		String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

		System.out.println(generatedString);
	}
	
	public static void getCurrentDate() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43	
	}
	
	public static void handlePagination() {
		
	}
	
	public static void read_PDF(String filePath) throws InvalidPasswordException, IOException {
		
		PDDocument document = PDDocument.load(new File(filePath));

		document.getClass();

		if (!document.isEncrypted()) {

			PDFTextStripperByArea stripper = new PDFTextStripperByArea();
			stripper.setSortByPosition(true);

			PDFTextStripper tStripper = new PDFTextStripper();

			String pdfFileInText = tStripper.getText(document);
		
			String lines[] = pdfFileInText.split("\\r?\\n");
			for (String line : lines) {
				System.out.println(line);
			}

		}

	}
	
	public static void read_PDF_pageWise(String FILE_NAME) {

		PdfReader reader;

		try {

			reader = new PdfReader(FILE_NAME);

			// pageNumber = 1
			String textFromPage = PdfTextExtractor.getTextFromPage(reader, 1);

			System.out.println(textFromPage);

			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void write_PDF(String FILE_NAME) {
		Document document = new Document();

		try {

			PdfWriter.getInstance(document, new FileOutputStream(new File(FILE_NAME)));

			// open
			document.open();

			Paragraph p = new Paragraph();
			p.add("This is my paragraph 1");
			// p.setAlignment(Element.ATTRIBUTE_NODE);

			document.add(p);

			Paragraph p2 = new Paragraph();
			p2.add("This is my paragraph 2"); // no alignment

			document.add(p2);

			Font f = new Font();
			f.setStyle(Font.BOLD);
			f.setSize(8);

			document.add(new Paragraph("This is my paragraph 3", f));

			// close
			document.close();

			System.out.println("Done");

		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	
	public static void pdf_Comparison(String file1, String file2, String result) throws Exception 
	{
		boolean test=new PdfComparator("file1", "file2").compare().writeTo("result");
		System.out.println(test);
	}

	public static void alertPopUpHandle() {
		Alert alert = driver.switchTo().alert();

		System.out.println(alert.getText());

		String text = alert.getText();
		System.out.println(text);

		alert.accept(); // click on OK btn

		// alert.dismiss(); //click on cancel btn
	}
	
	public static void calendarSelectTest(String xpath_month, String xpath_year) 
	{
		String date	= "dd-mmm-yyyy";
		String dateArr[] = date.split("-"); 
		String day = dateArr[0];
		String month = dateArr[1];
		String year = dateArr[2];
		
		Select select = new Select(driver.findElement(By.xpath(xpath_month)));
		select.selectByVisibleText(month);
		
		Select select1 = new Select(driver.findElement(By.xpath(xpath_year)));
		select1.selectByVisibleText(year);
		
		//*[@id='crmcalendar']/table/tbody/tr[2]/td/table/tbody/tr[2]/td[1]
		//*[@id='crmcalendar']/table/tbody/tr[2]/td/table/tbody/tr[2]/td[2]	
		//*[@id='crmcalendar']/table/tbody/tr[2]/td/table/tbody/tr[2]/td[6]
		
		String beforeXpath = "//*[@id='crmcalendar']/table/tbody/tr[2]/td/table/tbody/tr[";
		String afterXpath = "]/td[";
		
		final int totalWeekDays = 7;
		
		//2-1 2-2 2-3 2-4 2-5 2-6 2-7
		//3-2 3-2 3-3 3-4 3-5 3-6 3-7
		boolean flag = false;
		String dayVal = null;
		for(int rowNum=2; rowNum<=7; rowNum++){
			
			for(int colNum = 1; colNum<=totalWeekDays; colNum++){
				try{
			    dayVal =driver.findElement(By.xpath(beforeXpath+rowNum+afterXpath+colNum+"]")).getText();
				}catch (Exception e){
					System.out.println("Please enter a correct date value");
					flag = false;
					break;
				}
				System.out.println(dayVal);
				if(dayVal.equals(day)){
					driver.findElement(By.xpath(beforeXpath+rowNum+afterXpath+colNum+"]")).click();
					flag = true;
					break;
				}				
			}
			if(flag){
				break;
			}
			
		}
	
	}
	
	public static void dynamicWebTableHandle ()
	{
		//Method-1:
				String before_xpath = "//*[@id='vContactsForm']/table/tbody/tr[";
				String after_xpath = "]/td[2]/a";
				
				for(int i=4; i<=7; i++){
					String name = driver.findElement(By.xpath(before_xpath + i + after_xpath)).getText();
					System.out.println(name);
					if(name.contains("test2 test2")){ //i=6
						//*[@id='vContactsForm']/table/tbody/tr[6]/td[1]/input
						driver.findElement(By.xpath("//*[@id='vContactsForm']/table/tbody/tr["+i+"]/td[1]/input")).click();
					}
				}
				
		//Method-2:
				driver.findElement(By.xpath("//a[contains(text(),'test2 test2')]/parent::td//preceding-sibling::td//input[@name='contact_id']")).click();
				driver.findElement(By.xpath("//a[contains(text(),'ui uiii')]/parent::td//preceding-sibling::td//input[@name='contact_id']")).click();		
				
	}

	public static void handleWindowPopUp() throws Exception 
	{
		Set<String> handler = driver.getWindowHandles();

		Iterator<String> it = handler.iterator();

		String parentWindowId = it.next();
		System.out.println("parent window id:" + parentWindowId);

		String childWindowId = it.next();
		System.out.println("Child window id:" + childWindowId);

		driver.switchTo().window(childWindowId);

		Thread.sleep(2000);

		System.out.println("child window pop up title" + driver.getTitle());

		driver.close();

		driver.switchTo().window(parentWindowId);

		Thread.sleep(2000);

		System.out.println("parent window title" + driver.getTitle());
	}
	
	public void fileUploadUsingSenkeys(String xpath, String FILE_NAME) 
	{
		driver.findElement(By.xpath(xpath)).sendKeys(FILE_NAME);
	}
	
	public static void downloadFile(String xpath) throws InterruptedException 
	{
		driver.findElement(By.xpath(xpath)).click();
		Thread.sleep(5000);
		File[] listOfFiles=BrowserFactory.folder.listFiles();
			
		Assert.assertTrue(listOfFiles.length>0);
		
		for (File file : listOfFiles) {
			Assert.assertTrue(file.length()>0);
		}
	}
	
	
	
}
