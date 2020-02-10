package org.areco.ecommerce.cms.newsletter.controllers.cms;

import org.areco.ecommerce.cms.newsletter.model.NewsletterSignUpCMSComponentModel;

/**
 */
public interface ControllerConstants
{
	// Constant names cannot be changed due to their usage in dependant extensions, thus nosonar

	/**
	 * Class with action name constants
	 */
	interface Actions
	{
		interface Cms // NOSONAR
		{
			String _Prefix = "/view/"; // NOSONAR
			String _Suffix = "Controller"; // NOSONAR

			/**
			 * CMS components that have specific handlers
			 */
			String NewsletterSignUpCMSComponent = _Prefix + NewsletterSignUpCMSComponentModel._TYPECODE + _Suffix; // NOSONAR
		}
	}

	/**
	 * Class with view name constants
	 */
	interface Views
	{
		interface Cms // NOSONAR
		{
			String ComponentPrefix = "cms/"; // NOSONAR
		}

		interface Fragments
		{
			interface Newsletter // NOSONAR
			{
				String signUp = "newsletter/signUp"; // NOSONAR
			}
		}
	}
}
