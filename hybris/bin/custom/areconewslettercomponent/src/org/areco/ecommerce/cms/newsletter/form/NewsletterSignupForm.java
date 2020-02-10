package org.areco.ecommerce.cms.newsletter.form;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class NewsletterSignupForm {

	@Email
	private String emailAddress;

	@AssertTrue
	private boolean agreed;

	@NotBlank
	private String cmsComponentId;

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

	public String getCmsComponentId() {
		return cmsComponentId;
	}

	public void setCmsComponentId(final String pCmsComponentId) {
		cmsComponentId = pCmsComponentId;
	}
}
