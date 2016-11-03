package org.tix.controllers;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.tix.domain.Avatar;
import org.tix.messages.SuccessMessage;
import org.tix.services.AvatarService;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Dzianis_Haurylavets on 27.10.2016.
 */
@RestController
public class AvatarController {
	private static final Logger LOG = Logger.getLogger(AvatarController.class);
	private final AvatarService service;

	@Inject
	public AvatarController(AvatarService service) {
		this.service = service;
	}

	@GetMapping("/api/avatar/{id}")
	public ResponseEntity<Avatar> getAvatar(@PathVariable Long id){
		return ResponseEntity.ok(service.getAvatar(id));
	}

	@GetMapping("/api/avatar")
	public ResponseEntity<List<Avatar>> getAllAvatars(){
		return ResponseEntity.ok(service.getAvatars());
	}

	@PostMapping("/api/avatar")
	public ResponseEntity<Avatar> login(@RequestBody @Valid Avatar avatar){
		return ResponseEntity.ok(service.createAvatar(avatar));
	}

	@DeleteMapping("/api/avatar/{id}")
	public ResponseEntity<SuccessMessage<String>>
	logout(@PathVariable Long id, HttpSession session){
		service.unsafeDeleteAvatar(id);
		session.invalidate();

		return ResponseEntity.ok(new SuccessMessage<>("Logged out successfully"));
	}
}
