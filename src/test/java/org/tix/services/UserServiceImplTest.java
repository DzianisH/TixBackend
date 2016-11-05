package org.tix.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import org.tix.domain.User;
import org.tix.exceptions.NoSuchBeanException;
import org.tix.repositories.UserRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by DzianisH on 06.11.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
	@InjectMocks
	private UserServiceImpl service;
	@Mock
	private UserRepository repository;

	private Integer id = 5;
	private String email = "mockito@test.org", password = "unknown";
	private User dummyUser = new User(email, password).setId(id);

	@Test
	public void testGetUser_repositoryFindShouldInvokesOnce() throws Exception{
		when(repository.findByEmail(eq(email))).thenReturn(dummyUser);
		User answer = service.getUser(email, password);

		assertEquals(dummyUser, answer);
		verify(repository, times(1)).findByEmail(email);
	}

	@Test(expected = NoSuchBeanException.class)
	public void	testGetUser_shouldThrowOnRowEmailNotExists() throws Exception{
		when(repository.findByEmail(anyString())).thenReturn(null);
		service.getUser(email, password);
	}

	@Test(expected = NoSuchBeanException.class)
	public void testDeleteUser_shouldThrowIfIdIsNotUsed() throws Exception{
		when(repository.exists(anyInt())).thenReturn(true);
		when(repository.exists(eq(id))).thenReturn(false);
		doNothing().when(repository).delete(anyInt());
		doThrow(EmptyResultDataAccessException.class).when(repository).delete(eq(id));

		service.deleteUser(id);

		verify(repository, times(1)).delete(id);
	}

	@Test
	public void testDeleteUser_shouldNotThrowIfIdIsUsed() throws Exception{
		when(repository.exists(anyInt())).thenReturn(false);
		when(repository.exists(eq(id))).thenReturn(true);
		doThrow(EmptyResultDataAccessException.class).when(repository).delete(anyInt());
		doNothing().when(repository).delete(eq(id));

		try {
			service.deleteUser(id);
		} catch (NoSuchBeanException e){
			fail();
		}
		verify(repository, times(1)).delete(id);
	}

}
