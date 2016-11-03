package org.tix.services;

import org.tix.domain.User;
import org.tix.exceptions.NoSuchBeanException;

import java.util.List;

/**
 * Created by Dzianis_Haurylavets on 03.11.2016.
 */
public interface UserService {
	User getUser(Long id) throws NoSuchBeanException;

	List<User> getUsers();

	User createUser(User user);

	void deleteUser(Long id) throws NoSuchBeanException;
}
