package org.tix.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;
import org.tix.domain.Avatar;
import org.tix.domain.Session;
import org.tix.domain.User;
import org.tix.exceptions.BeanAlreadyInUse;
import org.tix.exceptions.NoSuchBeanException;
import org.tix.exceptions.NotAuthorisedException;
import org.tix.exceptions.PermissionDeniedException;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;

/**
 * Created by DzianisH on 05.11.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserSessionServiceImplTest {
	@InjectMocks
	private UserSessionServiceImpl sessionService;

	@Mock
	private UserService userService;
	@Mock
	private AvatarService avatarService;
	@Mock
	private Session session;

	private User dummyUser;
	private Avatar dummyAvatar;

	@Before
	public void setUp(){
		dummyUser = new User("mockito@test.org", "you_shall_not_pass");
		dummyAvatar = new Avatar(11, "mockito", dummyUser);
		dummyUser.setActiveAvatar(dummyAvatar);
	}

	@Test(expected = NotAuthorisedException.class)
	public void testIsAvatared_shouldThrowNotAuthorizedExceptionIfNoUserInSession() throws Exception{
		when(session.getUser()).thenReturn(null);
		sessionService.isAvatared();
	}

	@Test(expected = NotAuthorisedException.class)
	public void testGetActiveAvatar_shouldThrowNotAuthorizedExceptionIfNoUserInSession() throws Exception{
		when(session.getUser()).thenReturn(null);
		sessionService.getActiveAvatar();
	}

	@Test(expected = NotAuthorisedException.class)
	public void testUseAvatar_shouldThrowNotAuthorizedExceptionIfNoUserInSession() throws Exception{
		when(session.getUser()).thenReturn(null);

		sessionService.useAvatar(dummyAvatar);
	}

	@Test(expected = PermissionDeniedException.class)
	public void testUseAvatar_shouldThrowPermissionDeniedExceptionIfAvatarBelongToAnotherUser() throws Exception{
		User anotherUser = new User();
		when(session.getUser()).thenReturn(dummyUser);

		dummyAvatar.setUser(anotherUser);
		sessionService.useAvatar(dummyAvatar);
	}

	@Test
	public void testUseAvatar_shouldUpdateActiveAvatar() throws PermissionDeniedException {
		Avatar anotherAvatar = new Avatar();
		dummyUser.setActiveAvatar(anotherAvatar);
		when(session.getUser()).thenReturn(dummyUser);

		sessionService.useAvatar(dummyAvatar);
		assertEquals(dummyUser.getActiveAvatar(), dummyAvatar);
		verify(session, atLeastOnce()).getUser();
	}

	@Test(expected = NotAuthorisedException.class)
	public void testGetCurrentUser_shouldThrowNotAuthorizedExceptionOnNoActiveSession() throws Exception{
		sessionService.getCurrentUser();
	}

	@Test
	public void testLogout_shouldRemoveUserFromSession() throws Exception{
		sessionService.logout();
		verify(session, times(1)).setUser(null);
	}

	@Test
	public void testRelogin_shouldUpdateSessionUser() throws Exception{
		when(userService.getUser(eq(dummyUser.getEmail()), eq(dummyUser.getPassword())))
				.thenReturn(dummyUser);

		sessionService.relogin(dummyUser.getEmail(), dummyUser.getPassword());

		verify(userService, times(1)).getUser(dummyUser.getEmail(), dummyUser.getPassword());
		verify(session, times(1)).setUser(dummyUser);
	}

	@Test(expected = BeanAlreadyInUse.class)
	public void testCreateAvatar_shouldThrowBeanAlreadyInUseIfAvatarIsUsed() throws Exception{
		when(avatarService.getAvatar(anyLong())).thenThrow(new NoSuchBeanException());
		doReturn(dummyAvatar).when(avatarService).getAvatar(eq(dummyAvatar.getId()));
		when(session.getUser()).thenReturn(dummyUser);
		when(avatarService.isAvatarLoginFree(anyString())).thenReturn(true);
		when(avatarService.isAvatarLoginFree(eq(dummyAvatar.getLogin()))).thenReturn(false);

		sessionService.createAvatar(dummyAvatar);
	}

	@Test
	public void testCreateAvatar_shouldCreateAndSetAvatarIfItFree() throws Exception{
		when(avatarService.getAvatar(anyLong())).thenReturn(dummyAvatar);
		when(avatarService.getAvatar(eq(dummyAvatar.getId()))).thenThrow(new NoSuchBeanException());
		when(avatarService.isAvatarLoginFree(anyString())).thenReturn(Boolean.TRUE);
		when(avatarService.isAvatarLoginFree(eq(dummyAvatar.getLogin()))).thenReturn(Boolean.TRUE);
		when(session.getUser()).thenReturn(dummyUser);
		when(avatarService.unsafeCreateAvatar(eq(dummyAvatar))).thenReturn(dummyAvatar);

		dummyUser.setActiveAvatar(null);
		dummyAvatar.setUser(null);

		Avatar newAvatar = sessionService.createAvatar(dummyAvatar);

		// should return avatar from AvatarService
		assertTrue(newAvatar == dummyAvatar);
		// and dummy user should be owner of this avatar
		assertTrue(newAvatar.getUser() == dummyUser);
		// but should not set an active profile
		assertNull(dummyUser.getActiveAvatar());
	}


	@Test(expected = NotAuthorisedException.class)
	public void testIsAvatarBelongToCurrentUser_shouldThrowNotAuthorizedExceptionOnEmptySession() throws Exception{
		when(avatarService.getAvatar(eq(dummyAvatar.getId()))).thenReturn(dummyAvatar);
		sessionService.isAvatarBelongToCurrentUser(dummyAvatar.getId());
	}

	@Test
	public void	testIsAvatarBelongToCurrentUser_shouldReturnTrueIfYes() throws Exception{
		dummyUser.setActiveAvatar(null);
		when(session.getUser()).thenReturn(dummyUser);
		Avatar someAvatar = new Avatar(dummyAvatar.getColor(), dummyAvatar.getLogin(), dummyUser).setId(1L);
		when(avatarService.getAvatar(eq(1L))).thenReturn(someAvatar);


		assertTrue(sessionService.isAvatarBelongToCurrentUser(someAvatar.getId()));
	}

	@Test
	public void	testIsAvatarBelongToCurrentUser_shouldReturnFalseIfNo() throws Exception{
		dummyUser.setActiveAvatar(null);
		when(session.getUser()).thenReturn(dummyUser);

		Avatar someAvatar = new Avatar(dummyAvatar.getColor(), dummyAvatar.getLogin(), new User());
		assertFalse(sessionService.isAvatarBelongToCurrentUser(someAvatar));
	}

	@Test
	public void testDropAvatar_shouldTurnOffActiveAvatarIfItWasDropped() throws Exception{
		when(session.getUser()).thenReturn(dummyUser);

		sessionService.dropAvatar(dummyAvatar);
		assertNull(session.getUser().getActiveAvatar());
	}

	@Test
	public void testDropAvatar_shouldNotChangeActiveAvatarIfDroppedAnotherOne() throws Exception{
		when(session.getUser()).thenReturn(dummyUser);
		Avatar someActiveAvatar = new Avatar();
		dummyUser.setActiveAvatar(someActiveAvatar);

		sessionService.dropAvatar(dummyAvatar);
		assertNotNull(session.getUser().getActiveAvatar());
		assertEquals(someActiveAvatar, session.getUser().getActiveAvatar());
	}

	@Test(expected = PermissionDeniedException.class)
	public void testDropAvatar_shouldThrowPermissionDeniedExceptionIfAvatarNotBelongToCurrentUser() throws Exception{
		when(session.getUser()).thenReturn(dummyUser);
		dummyAvatar.setUser(new User());

		sessionService.dropAvatar(dummyAvatar);
	}

}
