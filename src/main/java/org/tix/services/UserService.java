package org.tix.services;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.tix.domain.Avatar;
import org.tix.exceptions.BeanAlreadyInUse;
import org.tix.exceptions.InvalidBeanException;
import org.tix.exceptions.NoSuchBeanException;

import javax.annotation.PreDestroy;
import java.util.List;

/**
 * Created by Dzianis_Haurylavets on 04.11.2016.
 */
@Component
@Scope("session")
public interface UserService {
	Avatar createAndUseAvatar(Avatar avatar)
			throws InvalidBeanException, BeanAlreadyInUse;

	boolean isActiveAvatarExists();
	Avatar getActiveAvatar();
	List<Avatar> getUserAvatarList();

	void dropAvatar(Avatar avatar) throws NoSuchBeanException;
//	/** drops all avatars */
//	void logout();
//
//	@PreDestroy
//	default void destroyUser(){
//		logout();
//	}

}
