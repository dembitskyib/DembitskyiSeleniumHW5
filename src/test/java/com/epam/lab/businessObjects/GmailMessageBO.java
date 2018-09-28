package com.epam.lab.businessObjects;

import org.openqa.selenium.WebDriver;

import com.epam.lab.pom.GmailHomePage;

public class GmailMessageBO {
	private GmailHomePage gmailHomePage;

	public GmailMessageBO(WebDriver driver) {
		gmailHomePage = new GmailHomePage(driver);
	}

	public void writeEmailAndSave(String receiver, String copyReceiver, String hiddenCopyReceiver, String subject,
			String messageText, int elementWaitTimeOut) {
		gmailHomePage.composeClick(elementWaitTimeOut);
		boolean isBlockOpened = false;
		isBlockOpened = gmailHomePage.isMessageBlockPresent(elementWaitTimeOut, isBlockOpened);
		gmailHomePage.typeReceiver(receiver);
		gmailHomePage.typeCopyReceiver(copyReceiver, elementWaitTimeOut);
		gmailHomePage.typeHiddenCopyReceiver(hiddenCopyReceiver, elementWaitTimeOut);
		gmailHomePage.typeSubject(subject);
		gmailHomePage.typeMessage(messageText);
		gmailHomePage.saveAndClose(elementWaitTimeOut);
		gmailHomePage.isMessageBlockPresent(elementWaitTimeOut, isBlockOpened);
	}

	public void openDraftAndSend(String receiver, String copyReceiver, String hiddenCopyReceiver, String subject,
			String messageText, String draftLettersURL, int elementWaitTimeOut, int pageUpdateTimeOut) {
		gmailHomePage.draftClick(pageUpdateTimeOut);
		gmailHomePage.lastMessageClick(draftLettersURL, pageUpdateTimeOut);
		if (gmailHomePage.checkComposeFields(receiver, copyReceiver, hiddenCopyReceiver, subject, messageText)) {
			gmailHomePage.clickSendButton();
		}
	}

	public boolean isEmailSendingSuccessful(int timeOut) {
		return gmailHomePage.isMessageSent(timeOut);
	}

}
