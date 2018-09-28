package com.epam.lab.businessObjects;

import org.openqa.selenium.WebDriver;

import com.epam.lab.pom.GmailLoginPage;

public class LoginBO {
	private GmailLoginPage gmailLoginPage;

	public LoginBO(WebDriver driver) {
		gmailLoginPage = new GmailLoginPage(driver);
	}

	public void logIn(String email, String password, int timeOut) {
		gmailLoginPage.typeEmailAndSubmit(email, timeOut);
		gmailLoginPage.typePasswordAndSubmit(password, timeOut);
	}
}
