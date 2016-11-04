package org.tix.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.tix.domain.User;
import org.tix.services.UserService;
import org.tix.services.UserSessionService;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * Created by Dzianis_Haurylavets on 04.11.2016.
 */
@RestController
public class UserController {
	private final UserService userService;

	private final UserSessionService sessionService;

	@Inject
	public UserController(UserService userService, UserSessionService sessionService) {
		this.userService = userService;
		this.sessionService = sessionService;
	}

	@GetMapping("/api/user/{email}/free")
	public ResponseEntity<Boolean> isEmailFree(@PathVariable String email){
		System.out.println(email);
		return ResponseEntity.ok(userService.isEmailFree(email));
	}

	@GetMapping("/api/user")
	public ResponseEntity<User> getYourUser(){
		return ResponseEntity.ok(new  User());
	}

	@PostMapping("/api/user")
	public ResponseEntity<User> createUser(@RequestBody @Valid User user){
		return ResponseEntity.ok(userService.createUser(user));
	}
}
