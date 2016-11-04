package org.tix.controllers;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tix.domain.Avatar;
import org.tix.messages.SuccessMessage;
import org.tix.services.AvatarService;
import org.tix.services.UserSessionService;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dzianis_Haurylavets on 27.10.2016.
 */
@RestController
public class AvatarController {
	private final AvatarService avatarService;
	private final UserSessionService sessionService;

	@Inject
	public AvatarController(AvatarService avatarService, UserSessionService sessionService) {
		this.avatarService = avatarService;
		this.sessionService = sessionService;
	}

	@GetMapping("/api/avatar/{id}")
	public ResponseEntity<Avatar> getAvatar(@PathVariable Long id){
		return ResponseEntity.ok(avatarService.getAvatar(id));
	}

	@GetMapping("/api/avatar")
	public ResponseEntity<List<Avatar>> getAllAvatars(){
		return ResponseEntity.ok(avatarService.getAvatarList());
	}

	@PostMapping("/api/avatar")
	public ResponseEntity<Avatar> createAvatar(@RequestParam @Valid Avatar avatar){
		return ResponseEntity.ok(sessionService.createAvatar(avatar));
	}

	@DeleteMapping({"/api/avatar/{id}", "/api/user/avatar/{id}"})
	public ResponseEntity<SuccessMessage<String>>
			dropAvatar(@PathVariable Long id){
		Avatar avatar = avatarService.getAvatar(id);
		sessionService.dropAvatar(avatar);

		return ResponseEntity.ok(new SuccessMessage<>("Avatar dropped: " + avatar));
	}

	@GetMapping("/api/user/avatar")
	public ResponseEntity<List<Avatar>> getYourAvatarList(){
		System.out.println("AAAAAAAAAAAAAAAAAA");
		return ResponseEntity.ok(sessionService.getUserAvatarList());
	}

	@GetMapping("/api/user/avatar/active")
	public ResponseEntity<Avatar> getYourAvatar(){
		return ResponseEntity.ok(sessionService.getActiveAvatar());
	}

	// TODO: May be PUT only?
	@RequestMapping(value = "/api/user/avatar/active", method = {RequestMethod.POST, RequestMethod.PUT})
	public ResponseEntity<Avatar> setYourAvatar(@RequestParam Long id){
		return ResponseEntity.ok(sessionService.useAvatar(id));
	}
}
