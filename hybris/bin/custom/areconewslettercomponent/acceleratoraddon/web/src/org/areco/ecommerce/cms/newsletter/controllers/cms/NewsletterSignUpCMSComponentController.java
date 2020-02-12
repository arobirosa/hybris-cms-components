package org.areco.ecommerce.cms.newsletter.controllers.cms;

import de.hybris.platform.addonsupport.controllers.cms.AbstractCMSAddOnComponentController;
import org.areco.ecommerce.cms.newsletter.model.NewsletterSignUpCMSComponentModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Returns the name of the view including addon path
 */
@Controller("NewsletterSignUpCMSComponentController")
@RequestMapping(value = ControllerConstants.Actions.Cms.NewsletterSignUpCMSComponent)
public class NewsletterSignUpCMSComponentController extends
				AbstractCMSAddOnComponentController<NewsletterSignUpCMSComponentModel> {

	@Override
	protected void fillModel(final HttpServletRequest request, final Model model,
					final NewsletterSignUpCMSComponentModel component) {
		// No custom fields are required.
	}



}
