package org.tix.controllers;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.tix.domain.User;
import org.tix.services.UserService;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Dzianis_Haurylavets on 27.10.2016.
 */
@RestController
public class UserController {
	private static final Logger LOG = Logger.getLogger(UserController.class);
	private final UserService service;

	@Inject
	public UserController(UserService service) {
		this.service = service;
	}

	@GetMapping("/api/user/{id}")
	public ResponseEntity<User> getUser(@PathVariable Long id){
		LOG.trace("UserController.getUser(Integer)");
		return new ResponseEntity<>(service.getUser(id), HttpStatus.OK);
	}

	@GetMapping("/api/user")
	public ResponseEntity<List<User>> getUsers(){
		LOG.trace("UserController.getUsers()");
		return ResponseEntity.ok(service.getUsers());
	}

}
