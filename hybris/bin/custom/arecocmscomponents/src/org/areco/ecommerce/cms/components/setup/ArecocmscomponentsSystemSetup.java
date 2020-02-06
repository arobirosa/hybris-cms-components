/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package org.areco.ecommerce.cms.components.setup;

import static org.areco.ecommerce.cms.components.constants.ArecocmscomponentsConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import org.areco.ecommerce.cms.components.constants.ArecocmscomponentsConstants;
import org.areco.ecommerce.cms.components.service.ArecocmscomponentsService;


@SystemSetup(extension = ArecocmscomponentsConstants.EXTENSIONNAME)
public class ArecocmscomponentsSystemSetup
{
	private final ArecocmscomponentsService arecocmscomponentsService;

	public ArecocmscomponentsSystemSetup(final ArecocmscomponentsService arecocmscomponentsService)
	{
		this.arecocmscomponentsService = arecocmscomponentsService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		arecocmscomponentsService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return ArecocmscomponentsSystemSetup.class.getResourceAsStream("/arecocmscomponents/sap-hybris-platform.png");
	}
}
