package org.tix.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tix.domain.User;
import org.tix.exceptions.NoSuchBeanException;
import org.tix.repositories.UserRepository;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

/**
 * Created by Dzianis_Haurylavets on 27.10.2016.
 */
@Service
public class UserServiceImpl implements UserService{
	private final static Logger LOG = Logger.getLogger(UserServiceImpl.class);
	private final UserRepository repository;

	@Inject
	public UserServiceImpl(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public User getUser(Long id) throws NoSuchBeanException{
		User user = repository.findOne(id);
		if(user != null) return user;

		final String msg = "Can't find User with id=" + id;
		LOG.warn(msg);
		throw new NoSuchBeanException(msg);
	}

	@Override
	public List<User> getUsers(){
		List<User> users = repository.findAll();
		if(users != null) return users;
		return Collections.emptyList();
	}

	@Override
	public User createOrUpdateUser(User user){
		return repository.save(user);
	}
}
