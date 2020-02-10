package org.areco.ecommerce.cms.newsletter.controllers.cms;

import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.contents.components.AbstractCMSComponentModel;
import de.hybris.platform.cms2.servicelayer.services.CMSComponentService;
import org.apache.log4j.Logger;
import org.areco.ecommerce.cms.newsletter.form.NewsletterSignupForm;
import org.areco.ecommerce.cms.newsletter.model.NewsletterSignUpCMSComponentModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Handles the submit of the newsletter form.
 */

@Controller
@RequestMapping(value = ControllerConstants.Actions.Cms.NewsletterSignUp)
public class NewsletterSubmissionController {

	@Resource(name = "cmsComponentService")
	private CMSComponentService cmsComponentService;

	@RequestMapping(method = RequestMethod.POST)
	public String signUp(@Valid @ModelAttribute final NewsletterSignupForm form, final BindingResult bindingResult, final Model pModel) {
		if (bindingResult.hasErrors()) {
			//TODO Show the errors to the customer
			return ControllerConstants.Views.Fragments.Newsletter.SubmissionError;
		}

		try {
			final AbstractCMSComponentModel abstractCMSComponent =
							cmsComponentService.getAbstractCMSComponent(form.getCmsComponentId());
			if (abstractCMSComponent instanceof NewsletterSignUpCMSComponentModel) {
				pModel.addAttribute("thankYouText", ((NewsletterSignUpCMSComponentModel) abstractCMSComponent).getThankYouText());
			} else {
				Logger.getLogger(this.getClass()).warn(String.format("The cms component with the uid '%s' is a %s", form.getCmsComponentId(), abstractCMSComponent.getClass().getSimpleName()));
				return ControllerConstants.Views.Fragments.Newsletter.SubmissionError;
			}
		} catch (CMSItemNotFoundException pCmsItemNotFoundException) {
			Logger.getLogger(this.getClass()).warn(String.format("Unable to find the cms component with the uid '%s'", form.getCmsComponentId()));
			return ControllerConstants.Views.Fragments.Newsletter.SubmissionError;
		}

		Logger.getLogger(this.getClass()).warn("A customer wants to sign up for the newsletter. Please implement this method.");
		//TODO Store the email in the database or call an external service. Send a verification email.

		return ControllerConstants.Views.Fragments.Newsletter.SubmissionSuccessful;
	}
}
