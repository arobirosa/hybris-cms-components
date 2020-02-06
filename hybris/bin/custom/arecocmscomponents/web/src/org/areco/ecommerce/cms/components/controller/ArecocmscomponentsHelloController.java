/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package org.areco.ecommerce.cms.components.controller;

import static org.areco.ecommerce.cms.components.constants.ArecocmscomponentsConstants.PLATFORM_LOGO_CODE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.areco.ecommerce.cms.components.service.ArecocmscomponentsService;


@Controller
public class ArecocmscomponentsHelloController
{
	@Autowired
	private ArecocmscomponentsService arecocmscomponentsService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String printWelcome(final ModelMap model)
	{
		model.addAttribute("logoUrl", arecocmscomponentsService.getHybrisLogoUrl(PLATFORM_LOGO_CODE));
		return "welcome";
	}
}
