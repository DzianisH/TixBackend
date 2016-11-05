package org.tix.services;

import org.tix.domain.Avatar;
import org.tix.domain.User;
import org.tix.exceptions.BeanAlreadyInUse;
import org.tix.exceptions.InvalidBeanException;
import org.tix.exceptions.NoSuchBeanException;
import org.tix.exceptions.NotAuthorisedException;
import org.tix.exceptions.PermissionDeniedException;

import java.util.List;

/**
 * Created by DzianisH on 04.11.2016.
 */
public interface UserSessionService {

	boolean isLoggedIn();
	boolean isAvatared() throws NotAuthorisedException;

	User getCurrentUser() throws NotAuthorisedException;
	Avatar getActiveAvatar() throws NotAuthorisedException;
	User relogin(String email, String password) throws NoSuchBeanException;
	void logout();

	Avatar createAvatar(Avatar avatar)
			throws InvalidBeanException, BeanAlreadyInUse, NotAuthorisedException;

	Avatar useAvatar(Long id) throws NoSuchBeanException, NotAuthorisedException, PermissionDeniedException;
	Avatar useAvatar(Avatar avatar) throws NotAuthorisedException, PermissionDeniedException;

	boolean isAvatarBelongToCurrentUser(Avatar avatar) throws NotAuthorisedException;
	boolean isAvatarBelongToCurrentUser(Long id) throws NotAuthorisedException, NoSuchBeanException;

	List<Avatar> getUserAvatarList() throws NotAuthorisedException;

	void dropAvatar(Avatar avatar) throws NoSuchBeanException, PermissionDeniedException;
}
