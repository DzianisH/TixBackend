package org.tix.services;

import org.tix.domain.User;
import org.tix.exceptions.InvalidBeanException;
import org.tix.exceptions.NoSuchBeanException;

/**
 * Created by DzianisH on 04.11.2016.
 */
public interface UserService {
	boolean isEmailFree(String email);
	User getUser(String email, String password) throws NoSuchBeanException;
	User createUser(User user) throws InvalidBeanException;
	void deleteUser(Integer id) throws NoSuchBeanException;
}
