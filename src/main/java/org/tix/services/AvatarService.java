package org.tix.services;

import org.tix.domain.Avatar;
import org.tix.exceptions.NoSuchBeanException;

import java.util.List;

/**
 * Created by Dzianis_Haurylavets on 03.11.2016.
 */
public interface AvatarService {
	boolean isAvatarLoginFree(String login);

	Avatar getAvatar(Long id) throws NoSuchBeanException;

	List<Avatar> getAvatarList();
	List<Avatar> getUserAvatarList(Integer userId);

	Avatar unsafeCreateAvatar(Avatar avatar);

	void unsafeDeleteAvatar(Long id) throws NoSuchBeanException;
}
