package org.tix.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by Dzianis_Haurylavets on 08.10.2016.
 */
@Controller
public class StaticContentController {
	private final Logger LOG = Logger.getLogger(getClass());

	@Value("${tix.static.root}")
	private String staticContentRoot;
	@Value("${tix.static.default}")
	private String defaultStaticFile;

	@PostConstruct
	public void onInit(){
		LOG.info("Handling static content in: " + staticContentRoot);
		defaultStaticFile = staticContentRoot + '/' + defaultStaticFile;
		LOG.info("Default static file: " + defaultStaticFile);
	}

	// too low-level!
	@GetMapping("${tix.static.mapping}")
	public void getIndexPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String path = staticContentRoot + request.getRequestURI();
		LOG.info("handling request to static: " + path);

		File file = new File(path);
		if(!file.exists() || file.isDirectory()){
			LOG.info("Can't find path, forwarding to " + path);
			path = defaultStaticFile;
			file = new File(path);
		}

		try(BufferedReader reader = new BufferedReader(new FileReader(file));
			PrintWriter writer = new PrintWriter(response.getWriter())){

			String line;
			while ((line = reader.readLine()) != null){
				writer.append(line).append('\n');
			}
		}
	}
}
