package com.qa.opencart.factory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {

	private Properties prop;
	private ChromeOptions co;
	private FirefoxOptions fo;
	private EdgeOptions eo;
	
	public OptionsManager(Properties prop) {
		this.prop = prop;
	}
	
	public ChromeOptions getChromeOptions() {
		co = new ChromeOptions();
		co.addArguments("--remote-allow-origins=*");
		if(Boolean.parseBoolean(prop.getProperty("headless"))) {
			System.out.println("------Running Chrome in Headless------");
			co.addArguments("--headless");	
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito")))	co.addArguments("--incognito");
		return co;
	}
	
	public FirefoxOptions getFireFoxOptions() {
		fo = new FirefoxOptions();
		fo.addArguments("--remote-allow-origins=*");
		if(Boolean.parseBoolean(prop.getProperty("headless"))) fo.addArguments("--headless");	
		if(Boolean.parseBoolean(prop.getProperty("incognito")))	fo.addArguments("--incognito");
		return fo;
	}
	
	public EdgeOptions getEdgeOptions() {
		eo = new EdgeOptions();
		eo.addArguments("--remote-allow-origins=*");
		if(Boolean.parseBoolean(prop.getProperty("headless"))) eo.addArguments("--headless");	
		if(Boolean.parseBoolean(prop.getProperty("incognito")))	eo.addArguments("--incognito");
		return eo;
	}
}
