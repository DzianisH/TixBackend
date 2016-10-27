package org.tix.services;

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
public class UserService {
	private final UserRepository repository;

	@Inject
	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	public User getUser(Integer id) throws NoSuchBeanException{
		User user = repository.findOne(id);
		if(user != null) return user;

		throw new NoSuchBeanException("Can't find User with id=" + id);
	}

	public List<User> getUsers(){
		List<User> users = repository.findAll();
		if(users != null) return users;
		return Collections.emptyList();
	}

	public void createOrUpdateUser(User user){
		repository.save(user);
	}
}
