package org.areco.ecommerce.cms.components.controller;

import de.hybris.platform.yacceleratorstorefront.controllers.cms.DefaultCMSComponentController;
import org.apache.log4j.Logger;
import org.areco.ecommerce.cms.components.form.NewsletterSignupForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Controller to handle the submit of the newsletter sign up form-
 */
@Controller("NewsletterSignUpCMSComponentController")
@RequestMapping(value = "/fragments/newsletter/signUp")
public class NewsletterSignUpCMSComponentController extends DefaultCMSComponentController {

	@RequestMapping(method = RequestMethod.POST)
	public void signUp(@Valid final NewsletterSignupForm form, final BindingResult bindingResult) {
		//TODO Check that the validation is working.

		Logger.getLogger(this.getClass()).warn("A customer wants to sign up for the newsletter. Please implement this method.");

		//TODO Store the email in the database or call an external service. Send a verification email.

		//TODO Return json object with a success or not.
	}
}
