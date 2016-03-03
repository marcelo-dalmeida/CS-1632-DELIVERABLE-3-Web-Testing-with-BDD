import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;



public class WikipediaTest {

	static WebDriver driver = new HtmlUnitDriver(true);

	// Start at the home page for reddit for each test
	@Before
	public void setUp() throws Exception {
		driver.get("https://en.wikipedia.org/wiki/Special:UserLogin/");
	}
	
	/*Given the registered Username
	And corresponding password
	When the user tries to login
	Then the user enters his personal unique space*/
	@Test
	public void testCorrectLogin() {
		
		String username = "UselessUser123";
		String password = "123456789";
		driver.findElement(By.linkText("Log in")).click();
		driver.findElement(By.name("wpName")).sendKeys(username);
		driver.findElement(By.name("wpPassword")).sendKeys(password);
		
		// Look for the submit button (in the login div) and click
		// to attempt to login 		
		WebElement loginDiv = driver.findElement(By.className("mw-ui-vform-field"));
		loginDiv.submit();
		
		String error_message = "many recent login attempts"; 
		String newPage = driver.getPageSource();
		String newPageTitle = driver.findElement(By.linkText(username)).getText();
		assertTrue(newPageTitle.contains(username) || newPage.contains(error_message));
		
		WebElement logout = driver.findElement(By.linkText("Log out"));
		logout.click();
	
	}
	
	/*Given a misspelled registered Username
	And corresponding password
	When the user tries to login
	Then a message written “There is no user by the name *****” appears*/
	@Test
	public void testWrongLoginUsername() {
		
		String username = "UselessUser124";
		String password = "123456789";
		driver.findElement(By.linkText("Log in")).click();
		driver.findElement(By.name("wpName")).clear();
		driver.findElement(By.name("wpName")).sendKeys(username);
		driver.findElement(By.name("wpPassword")).sendKeys(password);
		
		// Look for the submit button (in the login div) and click
		// to attempt to login 
		
		WebElement loginDiv = driver.findElement(By.name("userlogin"));
		loginDiv.submit();
	
		String newPage = driver.getPageSource();
		String error_message = "There is no user by the name \"" + username + "\"";
		String many_attempts_error_message = "You have made too many recent login attempts";
		assertTrue(newPage.contains(error_message) || newPage.contains(many_attempts_error_message));
		
	}


	/*Given a registered Username
	And misspelled corresponding password
	When the user tries to login
	Then a message written “Incorrect password entered“ appears*/
	@Test
	public void testWrongLoginPassword() {
		
		String username = "UselessUser123";
		String password = "1234567890";
		driver.findElement(By.linkText("Log in")).click();
		driver.findElement(By.name("wpName")).clear();
		driver.findElement(By.name("wpName")).sendKeys(username);
		driver.findElement(By.name("wpPassword")).sendKeys(password);
		
		// Look for the submit button (in the login div) and click
		// to attempt to login 
		
		WebElement loginDiv = driver.findElement(By.name("userlogin"));
		loginDiv.submit();		
		
		String newPage = driver.getPageSource();
		String error_message = "Incorrect password entered";
		String many_attempts_error_message = "You have made too many recent login attempts";
		assertTrue(newPage.contains(error_message) || newPage.contains(many_attempts_error_message));
		
	}
	
	/*Given the registered Username
	When the user tries to reset the password
	Then the user receives an email to his account associated with the Username*/
	@Test
	public void testResetPasswordWithCorrectUsername() {
		
		String username = "UselessUser123";
		//String email = "Darat1957@jourrapide.com";
		driver.findElement(By.linkText("Log in")).click();
		driver.findElement(By.linkText("Forgot your password?")).click();
		driver.findElement(By.name("wpUsername")).clear();
		driver.findElement(By.name("wpUsername")).sendKeys(username);
		
		// Look for the submit button (in the login div) and click
		// to attempt to login 
		
		WebElement loginDiv = driver.findElement(By.id("ooui-2"));
		loginDiv.submit();
		
		String newPage = driver.getPageSource();
		String message = "check"; // check e-mail
		String message2 = "password reset email will be sent";
		String many_attempts_error_message = "A password reminder has already been sent";
		String day_attempts_error_message = "performing this action too many times";
		assertTrue(newPage.contains(message) || newPage.contains(many_attempts_error_message)
				|| newPage.contains(day_attempts_error_message) || newPage.contains(message2));
	}
	
	/*Given the registered e-mail
	When the user tries to reset the password
	Then the user receives an email to his account associated*/
	@Test
	public void testResetPasswordWithCorrectEmail() {
	
		//String username = "UselessUser123";
		String email = "Darat1957@jourrapide.com";
		driver.findElement(By.linkText("Log in")).click();
		driver.findElement(By.linkText("Forgot your password?")).click();
		driver.findElement(By.name("wpEmail")).clear();
		driver.findElement(By.name("wpEmail")).sendKeys(email);
		
		// Look for the submit button (in the login div) and click
		// to attempt to login 
		
		WebElement loginDiv = driver.findElement(By.id("ooui-2"));
		loginDiv.submit();
		
		String newPage = driver.getPageSource();
		String message = "check"; // check e-mail
		String message2 = "password reset email will be sent";
		String day_attempts_error_message = "A password reminder has already been sent";
		String many_attempts_error_message = "performing this action too many times";
		assertTrue(newPage.contains(message) || newPage.contains(many_attempts_error_message) 
				|| newPage.contains(day_attempts_error_message) || newPage.contains(message2));
		
	}
	
	/*Given a misspelled registered Username
	When the user tries to reset the password
	Then a message written “There is no user by the name *****” appears*/
	@Test
	public void testResetPasswordWithIncorrectUsername() {
		
		String username = "UselessUser124";
		driver.findElement(By.linkText("Log in")).click();
		driver.findElement(By.linkText("Forgot your password?")).click();
		driver.findElement(By.name("wpUsername")).clear();
		driver.findElement(By.name("wpUsername")).sendKeys(username);
		
		// Look for the submit button (in the login div) and click
		// to attempt to login 
		
		WebElement loginDiv = driver.findElement(By.id("ooui-2"));
		loginDiv.submit();
		
		String newPage = driver.getPageSource();
		String error_message = "There is no user by the name \"" + username + "\"";
		String day_attempts_error_message = "A password reminder has already been sent";
		String many_attempts_error_message = "performing this action too many times";
		assertTrue(newPage.contains(error_message) || newPage.contains(many_attempts_error_message) 
				|| newPage.contains(day_attempts_error_message));
	
	}
	
	/*Given an unique Username 
	And matching password
	When the user tries to create an account
	Then the account is created and the user can use it*/
	@Test
	public void testCreateAccount() {
		
		String username = "UselessUser124";
		String password = "123456789";
		driver.findElement(By.linkText("Create account")).click();
		driver.findElement(By.name("wpName")).clear();
		driver.findElement(By.name("wpName")).sendKeys(username);
		driver.findElement(By.name("wpPassword")).sendKeys(password);
		driver.findElement(By.name("wpRetype")).sendKeys(password);
		
		WebElement createAccountDiv = driver.findElement(By.name("userlogin2"));
		createAccountDiv.submit();
		
		// Look for the submit button (in the login div) and click
		// to attempt to login 	
		
		String newPage = driver.getPageSource();
		String error_message = "Incorrect or missing CAPTCHA";
		assertTrue(newPage.contains(error_message));
			
	}
	
	/*Given an unique Username 
	And matching password
	And a valid e-mail address
	When the user tries to create an account
	Then the account is created and the user can use it*/
	@Test
	public void testCreateAccountWithOptionalEmail() {
		
		String username = "UselessUser124";
		String password = "123456789";
		String email = "Whisce1976@cuvox.de";
		driver.findElement(By.linkText("Create account")).click();
		driver.findElement(By.name("wpName")).clear();
		driver.findElement(By.name("wpName")).sendKeys(username);
		driver.findElement(By.name("wpPassword")).sendKeys(password);
		driver.findElement(By.name("wpRetype")).sendKeys(password);
		driver.findElement(By.name("wpEmail")).sendKeys(email);
		
		WebElement createAccountDiv = driver.findElement(By.name("userlogin2"));
		createAccountDiv.submit();
		
		// Look for the submit button (in the login div) and click
		// to attempt to login 	
		
		String newPage = driver.getPageSource();
		String error_message = "Incorrect or missing CAPTCHA";
		assertTrue(newPage.contains(error_message));		
	}
	
	/*Given an existing Username 
	When ~automatically~
	Then a message written “Username entered already in use”*/
	@Test
	public void testCreateAccountWithExistingUsername() {
		
		String username = "UselessUser123";
		driver.findElement(By.linkText("Create account")).click();
		driver.findElement(By.name("wpName")).clear();
		driver.findElement(By.name("wpName")).sendKeys(username);
		
		WebElement createAccountDiv = driver.findElement(By.name("userlogin2"));
		createAccountDiv.submit();
		
		// Look for the submit button (in the login div) and click
		// to attempt to login 
	
		String newPage = driver.getPageSource();
		String error_message = "Username entered already in use";
		assertTrue(newPage.contains(error_message));
			
	}

	/*Given an unique Username 
	And a not matching password
	When the user tries to create an account
	Then a message written “The passwords you entered do not match”*/
	@Test
	public void testCreateAccountWithNotMatchingPassword() {
		
		String username = "UselessUser124";
		String password = "123456789";
		String notMatchingPassword = "1234567890";
		driver.findElement(By.linkText("Create account")).click();
		driver.findElement(By.name("wpName")).clear();
		driver.findElement(By.name("wpName")).sendKeys(username);
		driver.findElement(By.name("wpPassword")).sendKeys(password);
		driver.findElement(By.name("wpRetype")).sendKeys(notMatchingPassword);
		
		WebElement createAccountDiv = driver.findElement(By.name("userlogin2"));
		createAccountDiv.submit();
		
		// Look for the submit button (in the login div) and click
		// to attempt to login 
	
		String newPage = driver.getPageSource();
		String error_message = "The passwords you entered do not match";
		assertTrue(newPage.contains(error_message));
			
	}
	
	/*Given an unique Username 
	And no password
	When the user tries to create an account
	Then a message written "Passwords must be at least 1 character"*/
	@Test
	public void testCreateAccountWithNoPassword() {
		
		String username = "UselessUser124";
		driver.findElement(By.linkText("Create account")).click();
		driver.findElement(By.name("wpName")).clear();
		driver.findElement(By.name("wpName")).sendKeys(username);
		
		WebElement createAccountDiv = driver.findElement(By.name("userlogin2"));
		createAccountDiv.submit();
		
		// Look for the submit button (in the login div) and click
		// to attempt to login 
	
		String newPage = driver.getPageSource();
		String error_message = "Passwords must be at least 1 character";
		assertTrue(newPage.contains(error_message));
			
	}
	
	/*Given an user and a search field
	When the user types “Apple” and search
	Then the user is redirected to the page describing the Apple fruit*/
	@Test
	public void testSearchApple() 
	{
		String apple = "Apple";
		driver.findElement(By.name("search")).clear();
		driver.findElement(By.name("search")).sendKeys(apple);
		
		WebElement searchDiv = driver.findElement(By.id("simpleSearch"));
		searchDiv.submit();
		
		// Look for the submit button (in the login div) and click
		// to attempt to login 
		
		WebElement firstHeading = driver.findElement(By.id("firstHeading"));
		String heading = firstHeading.getText();
		System.out.println(heading);
		if (heading.equals(apple)){
			assertTrue(heading.equals(apple));
		}
		else
		{
			WebElement firstLink = driver.findElement(By.className("mw-search-result-heading"));
			firstLink.findElement(By.linkText(apple)).click();
			
			firstHeading = driver.findElement(By.id("firstHeading"));
			heading = firstHeading.getText();
			assertTrue(heading.equals(apple));
		}	
	}
	
	/*Given an user and a search field
	When the user types “Apple” and search
	Then the user is redirected to the page describing the Apple fruit
	And a link for disambiguation is offered to him*/
	@Test
	public void testSearchAppleInc() 
	{
		String apple = "Apple";
		String appleCompany = "Apple Inc.";
		driver.findElement(By.name("search")).clear();
		driver.findElement(By.name("search")).sendKeys(apple);
		
		WebElement searchDiv = driver.findElement(By.id("simpleSearch"));
		searchDiv.submit();
		
		// Look for the submit button (in the login div) and click
		// to attempt to login 
		driver.findElement(By.linkText(apple + " (disambiguation)")).click();
		driver.findElement(By.linkText(appleCompany)).click();
		WebElement firstHeading = driver.findElement(By.id("firstHeading"));
		String heading = firstHeading.getText();
		System.out.println(heading);
		if (heading.equals(appleCompany)){
			assertTrue(heading.equals(appleCompany));
		}
		else
		{
			WebElement firstLink = driver.findElement(By.className("mw-search-result-heading"));
			firstLink.findElement(By.linkText(apple)).click();
			
			firstHeading = driver.findElement(By.id("firstHeading"));
			heading = firstHeading.getText();
			assertTrue(heading.equals(appleCompany));
		}
	}
	
	/*Given a user willing to donate
	When the user selects the one time, $5 amount and credit card options
	Then the user is redirected to the Credit card payment page*/
	@Test
	public void testOneTime5DonationCreditCard() 
	{
		String donation = "\"$5.00\"";
		driver.findElement(By.linkText("Donate to Wikipedia")).click();
		
		/*JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("<script type=\"text/javascript\" src=\"//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js\"></script>");
		js.executeScript("donationForm.toggleMonthly(false);");*/
		
		/*WebElement elem = driver.findElement(By.xpath("//*[@id=\"form-wrapper\"]"));
		String js = "arguments[0].style.display='block';";
		((JavascriptExecutor) driver).executeScript(js, elem);*/
		
		/*WebElement tmpElement = driver.findElement(By.id("frequency_onetime"));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", tmpElement);*/
		
		//System.out.println(driver.findElement(By.id("frequency_onetime")).isDisplayed());
		System.out.println(driver.getPageSource());
		driver.findElement(By.id("frequency_onetime")).click();
		driver.findElement(By.id("input_amount_0")).click();
		
		
		WebElement cardDiv = driver.findElement(By.className("payment-method-div monthly-capable paymentmethod-cc"));
		cardDiv.click();
		
		WebElement amountSpan = driver.findElement(By.id("selected-amount"));
		String amount = amountSpan.getText();
		assertTrue(amount.equals(donation));	
	}
	
	/*Given a user willing to donate
	When the user selects the monthly, $5 amount and credit card options
	Then the user is redirected to the Credit card payment page*/
	@Test
	public void testMonthly5DonationCreditCard() 
	{
		String donation = "\"$5.00\"";
		driver.findElement(By.linkText("Donate to Wikipedia")).click();
		
		
		/*JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("<script type=\"text/javascript\" src=\"//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js\"></script>");
		js.executeScript("donationForm.toggleMonthly(false);");*/
		
		/*WebElement elem = driver.findElement(By.xpath("//*[@id=\"form-wrapper\"]"));
		String js = "arguments[0].style.display='block';";
		((JavascriptExecutor) driver).executeScript(js, elem);*/
		
		/*WebElement tmpElement = driver.findElement(By.id("frequency_onetime"));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", tmpElement);*/
		
		//System.out.println(driver.findElement(By.id("frequency_onetime")).isDisplayed());
		System.out.println(driver.getPageSource());
		driver.findElement(By.id("frequency_monthly")).click();
		driver.findElement(By.id("input_amount_0")).click();
		
		
		WebElement cardDiv = driver.findElement(By.className("payment-method-div monthly-capable paymentmethod-cc"));
		cardDiv.click();
		
		WebElement amountSpan = driver.findElement(By.id("selected-amount"));
		String amount = amountSpan.getText();
		assertTrue(amount.equals(donation));	
	}
	
	/*Given a user willing to donate
	When the user selects the one time, $100 amount and credit card options
	Then the user is redirected to the Credit card payment page*/
	@Test
	public void testOneTime100DonationCreditCard() 
	{
		String donation = "\"$100.00\"";
		driver.findElement(By.linkText("Donate to Wikipedia")).click();
		
		
		/*JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("<script type=\"text/javascript\" src=\"//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js\"></script>");
		js.executeScript("donationForm.toggleMonthly(false);");*/
		
		/*WebElement elem = driver.findElement(By.xpath("//*[@id=\"form-wrapper\"]"));
		String js = "arguments[0].style.display='block';";
		((JavascriptExecutor) driver).executeScript(js, elem);*/
		
		/*WebElement tmpElement = driver.findElement(By.id("frequency_onetime"));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", tmpElement);*/
		
		//System.out.println(driver.findElement(By.id("frequency_onetime")).isDisplayed());
		System.out.println(driver.getPageSource());
		driver.findElement(By.id("frequency_onetime")).click();
		driver.findElement(By.id("input_amount_5")).click();
		
		
		WebElement cardDiv = driver.findElement(By.className("payment-method-div monthly-capable paymentmethod-cc"));
		cardDiv.click();
		
		WebElement amountSpan = driver.findElement(By.id("selected-amount"));
		String amount = amountSpan.getText();
		assertTrue(amount.equals(donation));	
	}
	
	/*Given a user willing to donate
	When the user selects the monthly, $100 amount and credit card options
	Then the user is redirected to the Credit card payment page*/
	@Test
	public void testMonthly100DonationCreditCard() 
	{
		String donation = "\"$100.00\"";
		driver.findElement(By.linkText("Donate to Wikipedia")).click();
		
		
		/*JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("<script type=\"text/javascript\" src=\"//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js\"></script>");
		js.executeScript("donationForm.toggleMonthly(false);");*/
		
		/*WebElement elem = driver.findElement(By.xpath("//*[@id=\"form-wrapper\"]"));
		String js = "arguments[0].style.display='block';";
		((JavascriptExecutor) driver).executeScript(js, elem);*/
		
		/*WebElement tmpElement = driver.findElement(By.id("frequency_onetime"));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", tmpElement);*/
		
		//System.out.println(driver.findElement(By.id("frequency_onetime")).isDisplayed());
		System.out.println(driver.getPageSource());
		driver.findElement(By.id("frequency_monthly")).click();
		driver.findElement(By.id("input_amount_5")).click();
		
		
		WebElement cardDiv = driver.findElement(By.className("payment-method-div monthly-capable paymentmethod-cc"));
		cardDiv.click();
		
		WebElement amountSpan = driver.findElement(By.id("selected-amount"));
		String amount = amountSpan.getText();
		assertTrue(amount.equals(donation));	
	}
	
	/*Given a user willing to donate
	When the user selects the one time, other amount (e.g. $5000) and credit card options
	Then the user is redirected to the Credit card payment page*/
	@Test
	public void testOneTimeOtherDonationCreditCard() 
	{
		String donation = "5000.00";
		driver.findElement(By.linkText("Donate to Wikipedia")).click();
		
		
		/*JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("<script type=\"text/javascript\" src=\"//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js\"></script>");
		js.executeScript("donationForm.toggleMonthly(false);");*/
		
		/*WebElement elem = driver.findElement(By.xpath("//*[@id=\"form-wrapper\"]"));
		String js = "arguments[0].style.display='block';";
		((JavascriptExecutor) driver).executeScript(js, elem);*/
		
		/*WebElement tmpElement = driver.findElement(By.id("frequency_onetime"));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", tmpElement);*/
		
		//System.out.println(driver.findElement(By.id("frequency_onetime")).isDisplayed());
		System.out.println(driver.getPageSource());
		driver.findElement(By.id("frequency_onetime")).click();
		driver.findElement(By.id("input_amount_other_box")).sendKeys(donation);
		
		
		WebElement cardDiv = driver.findElement(By.className("payment-method-div monthly-capable paymentmethod-cc"));
		cardDiv.click();
		
		WebElement amountSpan = driver.findElement(By.id("selected-amount"));
		String amount = amountSpan.getText();
		assertTrue(amount.equals(donation));	
	}
	
	/*Given a user willing to donate
	When the user selects the monthly, other amount (e.g. $5000) and credit card options
	Then the user is redirected to the Credit card payment page*/
	@Test
	public void testMontlyOtherDonationCreditCard() 
	{
		String donation = "5000";
		driver.findElement(By.linkText("Donate to Wikipedia")).click();
		
		
		/*JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("<script type=\"text/javascript\" src=\"//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js\"></script>");
		js.executeScript("donationForm.toggleMonthly(false);");*/
		
		/*WebElement elem = driver.findElement(By.xpath("//*[@id=\"form-wrapper\"]"));
		String js = "arguments[0].style.display='block';";
		((JavascriptExecutor) driver).executeScript(js, elem);*/
		
		/*WebElement tmpElement = driver.findElement(By.id("frequency_onetime"));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", tmpElement);*/
		
		//System.out.println(driver.findElement(By.id("frequency_onetime")).isDisplayed());
		System.out.println(driver.getPageSource());
		driver.findElement(By.id("frequency_monthly")).click();
		driver.findElement(By.id("input_amount_other_box")).sendKeys(donation);
		
		
		WebElement cardDiv = driver.findElement(By.className("payment-method-div monthly-capable paymentmethod-cc"));
		cardDiv.click();
		
		WebElement amountSpan = driver.findElement(By.id("selected-amount"));
		String amount = amountSpan.getText();
		assertTrue(amount.equals(donation));	
	}
	
	/*Given a user willing to donate
	When the user selects the one time, $5 amount and PayPal options
	Then the user is redirected to the PayPal payment page*/
	@Test
	public void testOneTime5DonationPayPal() 
	{
		String donation = "\"$5.00\"";
		driver.findElement(By.linkText("Donate to Wikipedia")).click();
		
		/*JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("<script type=\"text/javascript\" src=\"//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js\"></script>");
		js.executeScript("donationForm.toggleMonthly(false);");*/
		
		/*WebElement elem = driver.findElement(By.xpath("//*[@id=\"form-wrapper\"]"));
		String js = "arguments[0].style.display='block';";
		((JavascriptExecutor) driver).executeScript(js, elem);*/
		
		/*WebElement tmpElement = driver.findElement(By.id("frequency_onetime"));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", tmpElement);*/
		
		//System.out.println(driver.findElement(By.id("frequency_onetime")).isDisplayed());
		System.out.println(driver.getPageSource());
		driver.findElement(By.id("frequency_onetime")).click();
		driver.findElement(By.id("input_amount_0")).click();
		
		WebElement cardDiv = driver.findElement(By.className("payment-method-div monthly-capable paymentmethod-pp"));
		cardDiv.click();
		
		WebElement amountSpan = driver.findElement(By.id("selected-amount"));
		String amount = amountSpan.getText();
		assertTrue(amount.equals(donation));	
	}
	
	/*Given a user willing to donate
	When the user selects the one time, $5 amount and PayPal options
	Then the user is redirected to the PayPal payment page*/
	@Test
	public void testMontly5DonationPayPal() 
	{
		String donation = "\"$5.00\"";
		driver.findElement(By.linkText("Donate to Wikipedia")).click();
		
		/*JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("<script type=\"text/javascript\" src=\"//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js\"></script>");
		js.executeScript("donationForm.toggleMonthly(false);");*/
		
		/*WebElement elem = driver.findElement(By.xpath("//*[@id=\"form-wrapper\"]"));
		String js = "arguments[0].style.display='block';";
		((JavascriptExecutor) driver).executeScript(js, elem);*/
		
		/*WebElement tmpElement = driver.findElement(By.id("frequency_onetime"));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", tmpElement);*/
		
		//System.out.println(driver.findElement(By.id("frequency_onetime")).isDisplayed());
		System.out.println(driver.getPageSource());
		driver.findElement(By.id("frequency_monthly")).click();
		driver.findElement(By.id("input_amount_0")).click();
		
		WebElement cardDiv = driver.findElement(By.className("payment-method-div monthly-capable paymentmethod-pp"));
		cardDiv.click();
		
		WebElement amountSpan = driver.findElement(By.id("selected-amount"));
		String amount = amountSpan.getText();
		assertTrue(amount.equals(donation));	
	}
}
