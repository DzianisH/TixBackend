package org.tix.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Dzianis_Haurylavets on 08.10.2016.
 */
@Controller
public class DefaultController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping("/some/path")
	public String getIndexForward(){
		return "index";
	}
}
