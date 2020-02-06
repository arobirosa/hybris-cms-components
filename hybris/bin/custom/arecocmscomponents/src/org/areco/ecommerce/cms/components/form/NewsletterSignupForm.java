package org.areco.ecommerce.cms.components.form;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;

public class NewsletterSignupForm {

	@Email
	private String emailAddress;

	@AssertTrue
	private boolean agreed;

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(final String pEmailAddress) {
		emailAddress = pEmailAddress;
	}

	public boolean isAgreed() {
		return agreed;
	}

	public void setAgreed(final boolean pAgreed) {
		agreed = pAgreed;
	}
}
