package org.tix.controllers;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tix.domain.Avatar;
import org.tix.exceptions.InvalidBeanException;
import org.tix.exceptions.NoSuchBeanException;
import org.tix.exceptions.NotAuthorisedException;
import org.tix.exceptions.PermissionDeniedException;
import org.tix.messages.SuccessMessage;
import org.tix.services.AvatarService;
import org.tix.services.UserSessionService;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

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
	public ResponseEntity<Avatar> getAvatar(@PathVariable Long id) throws NoSuchBeanException {
		return ResponseEntity.ok(avatarService.getAvatar(id));
	}

	@GetMapping("/api/avatar")
	public ResponseEntity<List<Avatar>> getAllAvatars(){
		return ResponseEntity.ok(avatarService.getAvatarList());
	}

	@PostMapping({"/api/avatar", "/api/user/avatar"})
	public ResponseEntity<Avatar> createAvatar(@RequestParam @Valid Avatar avatar) throws InvalidBeanException, NotAuthorisedException {
		return ResponseEntity.ok(sessionService.createAvatar(avatar));
	}

	@DeleteMapping({"/api/avatar/{id}", "/api/user/avatar/{id}"})
	public ResponseEntity<SuccessMessage<String>>
			dropAvatar(@PathVariable Long id) throws NoSuchBeanException, PermissionDeniedException {
		Avatar avatar = avatarService.getAvatar(id);
		sessionService.dropAvatar(avatar);

		return ResponseEntity.ok(new SuccessMessage<>("Avatar dropped: " + avatar));
	}

	@GetMapping("/api/user/avatar")
	public ResponseEntity<List<Avatar>> getYourAvatarList() throws NotAuthorisedException {
		return ResponseEntity.ok(sessionService.getUserAvatarList());
	}

	@GetMapping("/api/user/avatar/active")
	public ResponseEntity<Avatar> getYourAvatar() throws NotAuthorisedException {
		return ResponseEntity.ok(sessionService.getActiveAvatar());
	}

	@PutMapping("/api/user/avatar/active")
	public ResponseEntity<Avatar> setYourAvatar(@RequestParam Long id) throws NoSuchBeanException, PermissionDeniedException {
		return ResponseEntity.ok(sessionService.useAvatar(id));
	}
}
