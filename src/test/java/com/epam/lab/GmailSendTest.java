package com.epam.lab;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.lab.businessObjects.GmailMessageBO;
import com.epam.lab.businessObjects.LoginBO;
import com.epam.lab.parsers.PropertyParser;
import com.epam.lab.parsers.XMLParser;

public class GmailSendTest {
	private final String PROPERTIES_PATH = "src/test/resources/config.properties";
	private WebDriver chromeDriver;
	private XMLParser xmlParser;
	private PropertyParser propertyParser;
	private String receiver;
	private String copyReceiver;
	private String hiddenCopyReceiver;
	private String subject;
	private String messageText;
	private int elementWaitTimeOut;

	@BeforeClass
	public void driverSetup() {
		propertyParser = new PropertyParser(PROPERTIES_PATH);
		xmlParser = new XMLParser(propertyParser.getProperty("xmlPath"));
		System.setProperty("webdriver.chrome.driver", propertyParser.getProperty("chromeDriverPath"));
		chromeDriver = new ChromeDriver();
		chromeDriver.manage().timeouts().implicitlyWait(Integer.parseInt(propertyParser.getProperty("implicitlyWait")),
				TimeUnit.SECONDS);
		chromeDriver.get(xmlParser.getProperty("homePageURL"));
		receiver = xmlParser.getProperty("to");
		copyReceiver = xmlParser.getProperty("cc");
		hiddenCopyReceiver = xmlParser.getProperty("bcc");
		subject = xmlParser.getProperty("subject");
		messageText = xmlParser.getProperty("text");
		elementWaitTimeOut = Integer.parseInt(propertyParser.getProperty("pageElementChangeTimeOut"));
	}

	@Test
	public void gmailSaveAndSendTest() {
		LoginBO loginBO = new LoginBO(chromeDriver);
		loginBO.logIn(xmlParser.getProperty("email"), xmlParser.getProperty("password"), elementWaitTimeOut);
		GmailMessageBO gmailMessageBO = new GmailMessageBO(chromeDriver);
		gmailMessageBO.writeEmailAndSave(receiver, copyReceiver, hiddenCopyReceiver, subject, messageText,
				elementWaitTimeOut);
		gmailMessageBO.openDraftAndSend(receiver, copyReceiver, hiddenCopyReceiver, subject, messageText,
				xmlParser.getProperty("draftLettersURL"), elementWaitTimeOut,
				Integer.parseInt(propertyParser.getProperty("pageUpdateTimeOut")));
		Assert.assertTrue(gmailMessageBO
				.isEmailSendingSuccessful(Integer.parseInt(propertyParser.getProperty("pageUpdateTimeOut"))));
	}

	@AfterClass
	public void driverQuit() {
		chromeDriver.quit();
	}
}
