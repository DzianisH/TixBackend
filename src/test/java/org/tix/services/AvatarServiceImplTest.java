package org.tix.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import org.tix.domain.Avatar;
import org.tix.exceptions.InvalidBeanException;
import org.tix.exceptions.NoSuchBeanException;
import org.tix.repositories.AvatarRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created by Dzianis_Haurylavets on 27.10.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class AvatarServiceImplTest {
	@InjectMocks
	private AvatarServiceImpl service;
	@Mock
	private AvatarRepository repository;

	private List<Avatar> emptyAvatarList = Collections.emptyList();
	private Long id = 5L;
	private Integer userId = 6;
	private Avatar dummyAvatar = new Avatar().setId(id);

	@Test
	public void testGetAvatarList_repositoryFindAllShouldInvokesOnce() throws Exception {
		when(repository.findAll()).thenReturn(emptyAvatarList);
		List<Avatar> answer = service.getAvatarList();

		assertEquals(emptyAvatarList, answer);
		verify(repository, times(1)).findAll();
	}

	@Test
	public void testGetUserAvatarList_repositoryFindAllByUserIdShouldInvokesOnce() throws Exception {
		when(repository.findAllByUserId(eq(userId))).thenReturn(emptyAvatarList);
		List<Avatar> answer = service.getUserAvatarList(userId);

		assertEquals(emptyAvatarList, answer);
		verify(repository, times(1)).findAllByUserId(userId);
	}

	@Test
	public void	testGetAvatarList_shouldReturnEmptyListOnNoAvatars() throws Exception{
		when(repository.findAll()).thenReturn(null);
		List<Avatar> answer = service.getAvatarList();

		assertNotNull(answer);
		assertEquals(answer.size(), 0);
	}

	@Test
	public void testGetAvatar_repositoryFindShouldInvokesOnce() throws Exception{
		when(repository.findOne(eq(id))).thenReturn(dummyAvatar);
		Avatar answer = service.getAvatar(id);

		assertEquals(dummyAvatar, answer);
		verify(repository, times(1)).findOne(id);
	}

	@Test(expected = NoSuchBeanException.class)
	public void	testGetAvatar_shouldThrowOnRowIdNotExists() throws Exception{
		when(repository.findOne(any(Long.class))).thenReturn(null);
		service.getAvatar(id);
	}

	@Test(expected = NoSuchBeanException.class)
	public void testUnsafeDeleteAvatar_shouldThrowIfLoginNotIsUsed() throws Exception{
		when(repository.exists(any(Long.class))).thenReturn(true);
		when(repository.exists(eq(id))).thenReturn(false);
		doNothing().when(repository).delete(any(Long.class));
		doThrow(EmptyResultDataAccessException.class).when(repository).delete(eq(id));

		service.unsafeDeleteAvatar(id);

		verify(repository, times(1)).delete(id);
	}

	@Test
	public void testUnsafeDeleteAvatar_shouldNotThrowIfLoginIsUsed() throws Exception{
		when(repository.exists(any(Long.class))).thenReturn(false);
		when(repository.exists(eq(id))).thenReturn(true);
		doThrow(EmptyResultDataAccessException.class).when(repository).delete(any(Long.class));
		doNothing().when(repository).delete(eq(id));

		try {
			service.unsafeDeleteAvatar(id);
		} catch (NoSuchBeanException e){
			fail();
		}
		verify(repository, times(1)).delete(id);
	}

}
