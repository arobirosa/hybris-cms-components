package org.areco.ecommerce.cms.newsletter.controllers.cms;

import de.hybris.platform.addonsupport.controllers.cms.AbstractCMSAddOnComponentController;
import org.areco.ecommerce.cms.newsletter.model.NewsletterSignUpCMSComponentModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller to handle the submit of the newsletter sign up form-
 */
@Controller("NewsletterSignUpCMSComponentController")
@RequestMapping(value = ControllerConstants.Actions.Cms.NewsletterSignUpCMSComponent)
public class NewsletterSignUpCMSComponentController extends
				AbstractCMSAddOnComponentController<NewsletterSignUpCMSComponentModel> {

	/*
	@RequestMapping(method = RequestMethod.POST)
	public void signUp(@Valid final NewsletterSignupForm form, final BindingResult bindingResult) {
		//TODO Check that the validation is working.

		Logger.getLogger(this.getClass()).warn("A customer wants to sign up for the newsletter. Please implement this method.");

		//TODO Store the email in the database or call an external service. Send a verification email.

		//TODO Return json object with a success or not.
	}*/

	@Override
	protected void fillModel(final HttpServletRequest request, final Model model,
					final NewsletterSignUpCMSComponentModel component) {
		model.addAttribute("Eureka", "eureka");
	}
}
