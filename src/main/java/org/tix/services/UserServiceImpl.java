package org.tix.services;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.tix.domain.User;
import org.tix.exceptions.InvalidBeanException;
import org.tix.exceptions.NoSuchBeanException;
import org.tix.repositories.UserRepository;

import javax.inject.Inject;
import javax.validation.ConstraintDeclarationException;

import static org.tix.utils.ObjectUtils.same;

/**
 * Created by Dzianis_Haurylavets on 04.11.2016.
 */
@Service
public class UserServiceImpl implements UserService{
	private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);

	private final UserRepository repository;

	@Inject
	public UserServiceImpl(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public boolean isEmailFree(String email) {
		return repository.findByEmail(email) == null;
	}

	@Override
	public User getUser(String email, String password) throws NoSuchBeanException{
		User user = repository.findByEmail(email);
		if(user != null && user.getPassword().equals(password)) return user;

		throw new NoSuchBeanException("Can't find user with email=" + email
			+ " and provided password");
	}

	@Override
	public User createUser(User user) throws InvalidBeanException {
		if(user == null) throw new InvalidBeanException("User can't be null");
		//TODO: Add to tests this case
		user.setId(null);
		try {
			return repository.save(user);
		} catch (ConstraintDeclarationException ex){
			LOG.warn("Attempting to save invalid avatar: " + ex.getLocalizedMessage(),
					ex);
			throw new InvalidBeanException("Validation failed for user:" + user
					+ " Probable reasons: not all fields specified"
					+ " or some fields are incorrect.", ex);
		}
	}

	@Override
	public void deleteUser(Integer id) throws NoSuchBeanException {
		try {
			repository.delete(id);
		} catch (EmptyResultDataAccessException ex){
			throw new NoSuchBeanException("Can't find User with id=" + id);
		}
	}
}
