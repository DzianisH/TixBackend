package org.tix.services;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.tix.domain.Avatar;
import org.tix.exceptions.InvalidBeanException;
import org.tix.exceptions.NoSuchBeanException;
import org.tix.repositories.AvatarRepository;

import javax.inject.Inject;
import javax.validation.ConstraintDeclarationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Dzianis_Haurylavets on 27.10.2016.
 */
@Service
public class AvatarServiceImpl implements AvatarService {
	private final static Logger LOG = Logger.getLogger(AvatarServiceImpl.class);
	private final AvatarRepository repository;

	@Inject
	public AvatarServiceImpl(AvatarRepository repository) {
		this.repository = repository;
	}

	@Override
	public Avatar getAvatar(Long id) throws NoSuchBeanException{
		Avatar avatar = repository.findOne(id);
		if(avatar != null) return avatar;

		final String msg = "Can't find Avatar with id=" + id;
		LOG.warn(msg);
		throw new NoSuchBeanException(msg);
	}

	@Override
	public boolean isAvatarLoginFree(String login){
		return repository.findByLogin(login) == null;
	}

	@Override
	public List<Avatar> getAvatarList(){
		List<Avatar> avatars = repository.findAll();
		if(avatars != null) return avatars;
		return new ArrayList<>();
	}

	@Override
	public List<Avatar> getUserAvatarList(Integer userId){
		List<Avatar> avatars = repository.findAllByUserId(userId);
		if(avatars != null) return avatars;
		return new ArrayList<>();
	}

	@Override
	public Avatar unsafeCreateAvatar(Avatar avatar) throws InvalidBeanException{
		avatar.setId(null);
		try {
			return repository.save(avatar);
		} catch (ConstraintDeclarationException ex){
			LOG.warn("Attempting to save invalid avatar: " + ex.getLocalizedMessage(),
					ex);
			throw new InvalidBeanException("Validation failed for avatar:" + avatar
					+ " Probable reasons: not all fields specified,"
					+ " login already exists. ", ex);
		}
	}

	@Override
	public void unsafeDeleteAvatar(Long id) throws NoSuchBeanException{
		try {
			repository.delete(id);
		} catch (EmptyResultDataAccessException ex){
			final String msg = "Can't find Avatar with id=" + id;
			LOG.warn(msg);
			throw new NoSuchBeanException(msg);
		}
	}
}
