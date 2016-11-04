package org.tix.services;

import org.tix.domain.User;

/**
 * Created by DzianisH on 04.11.2016.
 */
public interface UserService {
	boolean isEmailFree(String email);
	User getUser(String email, String password);
	boolean validate(User user);
	User createUser(User user);
	void deleteUser(User user);
}
