package org.tix.services;

import org.tix.domain.Avatar;
import org.tix.domain.User;
import org.tix.exceptions.BeanAlreadyInUse;
import org.tix.exceptions.InvalidBeanException;
import org.tix.exceptions.NoSuchBeanException;
import org.tix.exceptions.NotAuthorisedException;

import java.util.List;

/**
 * Created by DzianisH on 04.11.2016.
 */
public interface UserSessionService {

	boolean isLoggedIn();
	boolean isAvatared();

	User getCurrentUser();
	User relogin(String email, String password);
	void logout();

	Avatar createAvatar(Avatar avatar)
			throws InvalidBeanException, BeanAlreadyInUse, NotAuthorisedException;

	Avatar useAvatar(Long id);
	Avatar useAvatar(Avatar avatar);

	boolean isAvatarBelongToCurrentUser(Avatar avatar);
	boolean isAvatarBelongToCurrentUser(Long id);

	Avatar getActiveAvatar();
	List<Avatar> getUserAvatarList();

	void dropAvatar(Avatar avatar) throws NoSuchBeanException;
}
