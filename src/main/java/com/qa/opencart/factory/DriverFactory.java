package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.aspectj.util.FileUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.exception.FrameworkException;

public class DriverFactory {

	
	public WebDriver driver;
	public Properties prop;
	public OptionsManager optionsManager;
	
	public static String highlight;
	//tbrady@gmail.com    Test123!
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	
	public WebDriver initDriver(Properties prop) {
		
		optionsManager = new OptionsManager(prop);
		
		highlight = prop.getProperty("highlight");
		
		//String browserName = prop.getProperty("browser").toLowerCase().trim();
		String browserName = System.getProperty("browser");
		
		System.out.println("browser name is: "+browserName);
		
		if(browserName.trim().equalsIgnoreCase("chrome")) {
			//driver = new ChromeDriver(optionsManager.getChromeOptions());
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
		}
		else if(browserName.trim().equalsIgnoreCase("firefox")) {
			//driver = new FirefoxDriver(optionsManager.getFireFoxOptions());	
			tlDriver.set(new FirefoxDriver(optionsManager.getFireFoxOptions()));
		}
		else if(browserName.trim().equalsIgnoreCase("edge")) {
			//driver = new EdgeDriver(optionsManager.getEdgeOptions());	
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
		}
		else if(browserName.trim().equalsIgnoreCase("safari")) {
			driver = new SafariDriver();		
		}
		else {
			System.out.println("Plz pass the correct browser...: "+browserName);
			throw new FrameworkException("NO BROWSER FOUND EXCEPTION..");
		}
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		return getDriver();
	}
	
	public synchronized static WebDriver getDriver() {
		return tlDriver.get();
	}
	
	
	public Properties initProp(){
		prop = new Properties();
		FileInputStream fis = null;
		String envName = System.getProperty("env");
		System.out.println("Running test cases on Env: "+envName);
		
		try {
		if(envName == null) {
			System.out.println("No env is passed...");
			fis = new FileInputStream("./src/test/resources/config/config.properties");
		}
		
		else {
			switch(envName.toLowerCase().trim()) {
			case "normal":
				fis = new FileInputStream("./src/test/resources/config/config.properties");
				break;
			case "qa":
				fis = new FileInputStream("./src/test/resources/config/qa.config.properties");
				break;
			case "dev":
				fis = new FileInputStream("./src/test/resources/config/dev.config.properties");
				break;	
				
			default:
				System.out.println("Wrong env is passed..");
				throw new FrameworkException("WRONG ENV IS PASSED...");
				//break;
			}
		}
		
		}
		catch (FileNotFoundException e) {
			
		}
		
		try {
			prop.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
		
	}

	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileUtil.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	
}
