package org.tix.controllers;

import org.apache.log4j.Logger;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by DzianisH on 27.11.2016.
 */
@RestController
public class SocketEchoController {
	private static final Logger LOG = Logger.getLogger(SocketEchoController.class);


	@MessageMapping("/echo")
	@SendTo("/output/echo")
	public String getEcho(String message){
		LOG.info("Receive message '" + message + "' via WS.");

		if(message == null || message.trim().isEmpty()) return "You said nothing.";
		return "You said: " + message;
	}
}
