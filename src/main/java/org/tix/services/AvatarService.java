package org.tix.services;

import org.tix.domain.Avatar;
import org.tix.exceptions.NoSuchBeanException;

import java.util.List;

/**
 * Created by Dzianis_Haurylavets on 03.11.2016.
 */
public interface AvatarService {
	Avatar getAvatar(Long id) throws NoSuchBeanException;

	List<Avatar> getAvatars();

	Avatar createAvatar(Avatar avatar);

	void unsafeDeleteAvatar(Long id) throws NoSuchBeanException;
}
