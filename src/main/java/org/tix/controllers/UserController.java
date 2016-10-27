package org.tix.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.tix.responses.Response;
import org.tix.services.UserService;

import javax.inject.Inject;

/**
 * Created by Dzianis_Haurylavets on 27.10.2016.
 */
@RestController
public class UserController {
	private final UserService service;

	@Inject
	public UserController(UserService service) {
		this.service = service;
	}

	@GetMapping("/api/user/{id}")
	public Response getUser(@PathVariable Integer id){
		System.out.println("UserController.getUser");
		try {
			return Response.create(service.getUser(id));
		} catch (Throwable e){
			e.printStackTrace();
			return Response.create(e);
		}
	}

	@GetMapping({"/api/user/", "/api/user"})
	public Response getUsers(){
		System.out.println("UserController.getUser");
		try {
			return Response.create(service.getUsers());
		} catch (Throwable e){
			e.printStackTrace();
			return Response.create(e);
		}
	}

}
