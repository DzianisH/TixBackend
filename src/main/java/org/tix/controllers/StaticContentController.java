package org.tix.controllers;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Dzianis_Haurylavets on 08.10.2016.
 */
@Controller
public class StaticContentController {
	@GetMapping("/")
	public String getIndexPage(){
		return "index";
	}
}
