package org.tix.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.tix.domain.Avatar;
import org.tix.exceptions.NoSuchBeanException;
import org.tix.repositories.AvatarRepository;

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
public class AvatarServiceTest {
	@InjectMocks
	private AvatarServiceImpl service;
	@Mock
	private AvatarRepository repository;

	private List<Avatar> emptyAvatarList = Collections.emptyList();
	private Long id = 5L;
	private Avatar dummyAvatar = new Avatar().setId(id);

	@Test
	public void testGetAvatarss_repositoryFindAllShouldInvokesOnce() throws Exception {
		when(repository.findAll()).thenReturn(emptyAvatarList);
		List<Avatar> answer = service.getAvatars();

		assertEquals(emptyAvatarList, answer);
		verify(repository, times(1)).findAll();
	}

	@Test
	public void	testGetAvatars_shouldReturnEmptyListOnNoAvatars() throws Exception{
		when(repository.findAll()).thenReturn(null);
		List<Avatar> answer = service.getAvatars();

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
	public void	testGetAvatar_shouldThrowNowSuchBeanExceptionOnRowIdNotExists() throws Exception{
		when(repository.findOne(any(Long.class))).thenReturn(null);
		service.getAvatar(id);
	}
}
