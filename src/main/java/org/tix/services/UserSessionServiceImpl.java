package org.tix.services;

import org.springframework.stereotype.Service;
import org.tix.domain.Avatar;
import org.tix.domain.Session;
import org.tix.domain.User;
import org.tix.exceptions.BeanAlreadyInUse;
import org.tix.exceptions.InvalidBeanException;
import org.tix.exceptions.NoSuchBeanException;
import org.tix.exceptions.NotAuthorisedException;
import org.tix.exceptions.PermissionDeniedException;

import javax.inject.Inject;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import static org.tix.utils.ObjectUtils.same;

/**
 * Created by DzianisH on 04.11.2016.
 * TODO: Test me!
 */
@Service
public class UserSessionServiceImpl implements UserSessionService{
	private final UserService userService;
	private final AvatarService avatarService;
	private final Session session;

	@Inject
	public UserSessionServiceImpl(UserService userService, AvatarService avatarService, Session session) {
		this.userService = userService;
		this.avatarService = avatarService;
		this.session = session;
	}

	@Override
	public User getCurrentUser() throws NotAuthorisedException {
		User user = session.getUser();
		if(user != null) return user;
		throw new NotAuthorisedException("You should authenticate first");
	}

	@Override
	public boolean isLoggedIn(){
		return session.getUser() != null;
	}

	@Override
	public boolean isAvatared(){
		return session.getAvatar() != null;
	}

	@Override
	public User relogin(String email, String password) {
		User newUser = userService.getUser(email, password);
		session.setAvatar(null);
		session.setUser(newUser);
		return newUser;
	}

	@Override
	public void logout() {
		session.setUser(null);
		session.setAvatar(null);
	}

	@Override
	public Avatar createAvatar(Avatar avatar)
			throws InvalidBeanException, BeanAlreadyInUse, NotAuthorisedException {
		if(!isLoggedIn()) throw new NotAuthorisedException();
		User user = session.getUser();

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
			session.setAvatar(avatar);
			return avatar;
		}
		throw new PermissionDeniedException("Avatar with id=" + id +
			" is belong to another user");
	}

	@Override
	public boolean isAvatarBelongToCurrentUser(Avatar avatar){
		User activeUser = getCurrentUser();
		return same(activeUser, avatar.getUser());
	}

	@Override
	public boolean isAvatarBelongToCurrentUser(Long id){
		User activeUser = getCurrentUser();
		Avatar avatar = avatarService.getAvatar(id);
		return same(activeUser, avatar.getUser());
	}

	@Override
	public Avatar getActiveAvatar() {
		return session.getAvatar();
	}

	@Override
	public List<Avatar> getUserAvatarList() {
		if(!isLoggedIn()) throw new NotAuthorisedException();
		return avatarService.getUserAvatarList(session.getUser().getId());
	}

	@Override
	public void dropAvatar(Avatar avatar) throws NoSuchBeanException {
		if(isAvatarBelongToCurrentUser(avatar)){
			avatarService.unsafeDeleteAvatar(avatar.getId());
		}
		throw new PermissionDeniedException("This avatar belong to another user");
	}
}
