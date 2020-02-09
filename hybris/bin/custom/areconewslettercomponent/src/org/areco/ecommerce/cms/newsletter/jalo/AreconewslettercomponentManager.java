package org.areco.ecommerce.cms.newsletter.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import org.apache.log4j.Logger;
import org.areco.ecommerce.cms.newsletter.constants.AreconewslettercomponentConstants;

public class AreconewslettercomponentManager extends GeneratedAreconewslettercomponentManager
{
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger( AreconewslettercomponentManager.class.getName() );
	
	public static final AreconewslettercomponentManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (AreconewslettercomponentManager) em.getExtension(AreconewslettercomponentConstants.EXTENSIONNAME);
	}
	
}
