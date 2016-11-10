package org.tix.controllers;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tix.domain.User;
import org.tix.exceptions.InvalidBeanException;
import org.tix.exceptions.NoSuchBeanException;
import org.tix.exceptions.NotAuthorisedException;
import org.tix.services.UserService;
import org.tix.services.UserSessionService;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * Created by Dzianis_Haurylavets on 04.11.2016.
 */
@RestController
public class UserController {
	private final Logger LOG = Logger.getLogger(getClass());

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
	public ResponseEntity<User> getYourUser() throws NotAuthorisedException {
		return ResponseEntity.ok(sessionService.getCurrentUser());
	}

	@PostMapping("/api/user")
	public ResponseEntity<User> register(@RequestBody @Valid User user) throws InvalidBeanException, NoSuchBeanException {
		user = userService.createUser(user);
		return login(user);
	}

	@PostMapping("/api/login")
	public ResponseEntity<User> login(@RequestBody @Valid User user) throws NoSuchBeanException {
		LOG.debug("Invoking login for user: " + user);
		return ResponseEntity.ok(sessionService.relogin(user.getEmail(), user.getPassword()));
	}

	@PostMapping("/api/logout")
	public ResponseEntity<Boolean> logout(){
		sessionService.logout();
		return ResponseEntity.ok(true);
	}
}
