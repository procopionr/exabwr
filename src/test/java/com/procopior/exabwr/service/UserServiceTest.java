package com.procopior.exabwr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.procopior.exabwr.dto.UserDTO;
import com.procopior.exabwr.exception.UserException;
import com.procopior.exabwr.model.User;
import com.procopior.exabwr.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository repository;

	@InjectMocks
	private UserService service;

	private User user;

	private UserDTO dto;

	private static final String NAME = "RICARDO";

	private static final String USERNAME = "procopior";

	private static final String EMAIL = "procopior@gmail.com";

	@BeforeEach
	public void setup() {
		user = User.builder().username(USERNAME).name(NAME).email(EMAIL).build();
		dto = UserDTO.builder().username(USERNAME).name(NAME).email(EMAIL).build();
	}

	@Test
	void testSave() throws Exception {

		Mockito.when(this.repository.findByUsername(dto.getUsername())).thenReturn(Optional.empty());

		Mockito.when(this.repository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());

		final UserDTO userSaved = this.service.save(dto);

		Assertions.assertNotNull(userSaved);
	}

	@Test
	void testSaveThrowsEmail() throws Exception {

		Mockito.when(this.repository.findByEmail(dto.getEmail())).thenReturn(Optional.of(user));

		Assertions.assertThrows(UserException.class, () -> service.save(dto));
	}

	@Test
	void testSaveThrowsUsername() throws Exception {

		Mockito.when(this.repository.findByUsername(dto.getUsername())).thenReturn(Optional.of(user));

		Assertions.assertThrows(UserException.class, () -> service.save(dto));
	}

	@Test
	void testUpdate() throws Exception {

		Mockito.when(this.repository.findByUsername(dto.getUsername())).thenReturn(Optional.of(user));

		final UserDTO userUpdated = this.service.update(dto);

		Assertions.assertNotNull(userUpdated);
	}

	@Test
	void testUpdateThrowsUsername() throws Exception {

		Mockito.when(this.repository.findByUsername(dto.getUsername())).thenReturn(Optional.empty());

		Assertions.assertThrows(UserException.class, () -> service.update(dto));
	}

	@Test
	public void testDelete() throws UserException {

		Mockito.when(this.repository.findByUsername(dto.getUsername())).thenReturn(Optional.of(user));

		Mockito.doNothing().when(repository).deleteById(user.getUsername());

		service.delete(user.getUsername());

		Mockito.verify(this.repository, Mockito.times(1)).deleteById(user.getUsername());
	}

	@Test
	public void testDeleteThrow() throws UserException {

		Mockito.when(this.repository.findByUsername(dto.getUsername())).thenReturn(Optional.empty());

		Assertions.assertThrows(UserException.class, () -> service.delete(dto.getUsername()));
	}

	@Test
	void testFindAll() {

		final List<User> users = new ArrayList<>();
		users.add(user);
		users.add(User.builder().username(USERNAME + "1").name(NAME).email(EMAIL).build());

		Mockito.when(this.repository.findAll()).thenReturn(users);

		List<User> resultList = this.service.findAll();
		Assertions.assertEquals(2, resultList.size());

	}

}
