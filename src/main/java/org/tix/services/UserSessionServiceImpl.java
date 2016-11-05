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

import static org.tix.utils.ObjectUtils.same;

/**
 * Created by DzianisH on 04.11.2016.
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
	public boolean isAvatared() throws NotAuthorisedException{
		return getCurrentUser().getActiveAvatar() != null;
	}

	@Override
	public User relogin(String email, String password) throws NoSuchBeanException {
		User newUser = userService.getUser(email, password);
		session.setUser(newUser);
		return newUser;
	}

	@Override
	public void logout() {
		session.setUser(null);
	}

	@Override
	public Avatar createAvatar(Avatar avatar)
			throws InvalidBeanException, BeanAlreadyInUse, NotAuthorisedException {
		User user = getCurrentUser();

		if(avatar.getLogin() == null) {
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
	public Avatar useAvatar(Long id) throws NoSuchBeanException, PermissionDeniedException, NotAuthorisedException {
		return useAvatar(avatarService.getAvatar(id));
	}

	@Override
	public Avatar useAvatar(Avatar avatar) throws PermissionDeniedException, NotAuthorisedException {
		if(isAvatarBelongToCurrentUser(avatar)){
			getCurrentUser().setActiveAvatar(avatar);
			return avatar;
		}
		throw new PermissionDeniedException("Avatar with id=" + avatar.getId() +
			" is belong to another user");
	}

	@Override
	public boolean isAvatarBelongToCurrentUser(Avatar avatar) throws NotAuthorisedException {
		User activeUser = getCurrentUser();
		return same(activeUser, avatar.getUser());
	}

	@Override
	public boolean isAvatarBelongToCurrentUser(Long id) throws NotAuthorisedException, NoSuchBeanException{
		return isAvatarBelongToCurrentUser(avatarService.getAvatar(id));
	}

	@Override
	public Avatar getActiveAvatar() throws NotAuthorisedException {
		if(!isLoggedIn()) throw new NotAuthorisedException();
		return getCurrentUser().getActiveAvatar();
	}

	@Override
	public List<Avatar> getUserAvatarList() throws NotAuthorisedException{
		return avatarService.getUserAvatarList(getCurrentUser().getId());
	}

	@Override
	public void dropAvatar(Avatar avatar) throws NoSuchBeanException, PermissionDeniedException {
		if(isAvatarBelongToCurrentUser(avatar)){
			// TODO: thread safe!
			User currentUser = getCurrentUser();
			avatarService.unsafeDeleteAvatar(avatar.getId());
			if(same(avatar, currentUser.getActiveAvatar())){
				currentUser.setActiveAvatar(null);
			}
		} else {
			throw new PermissionDeniedException("This avatar belong to another user");
		}
	}
}
