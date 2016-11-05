package org.tix.services;

import org.springframework.stereotype.Service;
import org.tix.domain.Avatar;
import org.tix.domain.UserSession;
import org.tix.domain.User;
import org.tix.exceptions.BeanAlreadyInUse;
import org.tix.exceptions.InvalidBeanException;
import org.tix.exceptions.NoSuchBeanException;
import org.tix.exceptions.NotAuthorisedException;
import org.tix.exceptions.PermissionDeniedException;
import org.tix.utils.ObjectUtils;

import javax.inject.Inject;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

/**
 * Created by DzianisH on 04.11.2016.
 * TODO: Test me!
 */
@Service
public class UserSessionServiceImpl implements UserSessionService{
	private final UserService userService;
	private final AvatarService avatarService;
	private final UserSession userSession;

	@Inject
	public UserSessionServiceImpl(UserService userService, AvatarService avatarService, UserSession userSession) {
		this.userService = userService;
		this.avatarService = avatarService;
		this.userSession = userSession;
	}

	@Override
	public User getCurrentUser() throws NotAuthorisedException {
		User user = userSession.getUser();
		if(user != null) return user;
		throw new NotAuthorisedException("You should authenticate first");
	}

	@Override
	public boolean isLoggedIn(){
		return userSession.getUser() != null;
	}

	@Override
	public boolean isAvatared(){
		return userSession.getAvatar() != null;
	}

	@Override
	public User relogin(String email, String password) {
		User newUser = userService.getUser(email, password);
		userSession.setAvatar(null);
		userSession.setUser(newUser);
		return newUser;
	}

	@Override
	public void logout() {
		userSession.setUser(null);
		userSession.setAvatar(null);
	}

	@Override
	public Avatar createAvatar(Avatar avatar)
			throws InvalidBeanException, BeanAlreadyInUse, NotAuthorisedException {
		if(!isLoggedIn()) throw new NotAuthorisedException();
		User user = userSession.getUser();

		if(avatar.getLogin() != null) {
			throw new InvalidBeanException("login can't be null");
		}
		if(avatarService.isAvatarLoginFree(avatar.getLogin())){
			avatar.setUser(user);
			return avatarService.unsafeCreateAvatar(avatar);
		}
		throw new BeanAlreadyInUse("Avatar with login=" + avatar.getLogin() +
			"is already used");
	}

	@Override
	public Avatar useAvatar(Long id){
		return useAvatar(avatarService.getAvatar(id));
	}

	@Override
	public Avatar useAvatar(Avatar avatar){
		if(isAvatarBelongToCurrentUser(avatar)){
			userSession.setAvatar(avatar);
			return avatar;
		}
		throw new PermissionDeniedException("Avatar with id=" + id +
			" is belong to another user");
	}

	@Override
	public boolean isAvatarBelongToCurrentUser(Avatar avatar){
		User activeUser = getCurrentUser();
		return ObjectUtils.equals(activeUser, avatar.getUser());
	}

	@Override
	public boolean isAvatarBelongToCurrentUser(Long id){
		User activeUser = getCurrentUser();
		Avatar avatar = avatarService.getAvatar(id);
		return ObjectUtils.equals(activeUser, avatar.getUser());
	}

	@Override
	public Avatar getActiveAvatar() {
		return userSession.getAvatar();
	}

	@Override
	public List<Avatar> getUserAvatarList() {
		if(!isLoggedIn()) throw new NotAuthorisedException();
		return avatarService.getUserAvatarList(userSession.getUser().getId());
	}

	@Override
	public void dropAvatar(Avatar avatar) throws NoSuchBeanException {
		if(isAvatarBelongToCurrentUser(avatar)){
			avatarService.unsafeDeleteAvatar(avatar.getId());
		}
		throw new PermissionDeniedException("This avatar belong to another user");
	}
}
