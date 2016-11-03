package org.tix.controllers;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.tix.domain.User;
import org.tix.messages.SuccessMessage;
import org.tix.services.UserService;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
		return ResponseEntity.ok(service.getUser(id));
	}

	@GetMapping("/api/user")
	public ResponseEntity<List<User>> getUsers(){
		return ResponseEntity.ok(service.getUsers());
	}

	@PostMapping("/api/user")
	public ResponseEntity<User> login(@RequestBody @Valid User user){
		return ResponseEntity.ok(service.createUser(user));
	}

	@DeleteMapping("/api/user/{id}")
	public ResponseEntity<SuccessMessage<String>>
	logout(@PathVariable Long id, HttpSession session){
		service.deleteUser(id);
		session.invalidate();

		return ResponseEntity.ok(new SuccessMessage<>("Logged out successfully"));
	}
}
