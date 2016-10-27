package org.tix.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.tix.domain.User;
import org.tix.exceptions.NoSuchBeanException;
import org.tix.repositories.UserRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Dzianis_Haurylavets on 27.10.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class UsersServiceTest {
	@InjectMocks
	private UserService service;
	@Mock
	private UserRepository repository;

	private List<User> emptyUserList = Collections.emptyList();
	private Integer id = 5;
	private User dummyUser = new User(id);

	@Test
	public void testGetUsers_repositoryFindAllShouldInvokesOnce() throws Exception {
		when(repository.findAll()).thenReturn(emptyUserList);
		List<User> answer = service.getUsers();

		assertEquals(emptyUserList, answer);
		verify(repository, times(1)).findAll();
	}

	@Test
	public void	testGetUsers_shouldReturnEmptyListOnNoUsers() throws Exception{
		when(repository.findAll()).thenReturn(null);
		List<User> answer = service.getUsers();

		assertNotNull(answer);
		assertEquals(answer.size(), 0);
	}

	@Test
	public void testGetUser_repositoryFindShouldInvokesOnce() throws Exception{
		when(repository.findOne(eq(id))).thenReturn(dummyUser);
		User answer = service.getUser(id);

		assertEquals(dummyUser, answer);
		verify(repository, times(1)).findOne(id);
	}

	@Test(expected = NoSuchBeanException.class)
	public void	testGetUser_shouldThrowNowSuchBeanExceptionOnRowIdNotExists() throws Exception{
		when(repository.findOne(any(Integer.class))).thenReturn(null);
		service.getUser(id);
	}
}
