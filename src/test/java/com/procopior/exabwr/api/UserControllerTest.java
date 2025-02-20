package com.procopior.exabwr.api;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.procopior.exabwr.dto.UserDTO;
import com.procopior.exabwr.model.User;
import com.procopior.exabwr.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	@InjectMocks
	private UserController controller;

	@Mock
	private UserService service;

	private UserDTO dto;

	private User user;

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

		Mockito.when(this.service.save(dto)).thenReturn(dto);

		final UserDTO newUser = this.controller.save(dto);

		Assertions.assertNotNull(newUser);
	}

	@Test
	void testUpdate() throws Exception {

		Mockito.when(this.service.update(dto)).thenReturn(dto);

		final UserDTO userUpdated = this.controller.update(dto);

		Assertions.assertNotNull(userUpdated);
	}

	@Test
	void testFindAll() throws Exception {
		final List<User> users = new ArrayList<>();
		users.add(user);
		users.add(User.builder().username(USERNAME + "1").name(NAME).email(EMAIL).build());

		Mockito.when(this.service.findAll()).thenReturn(users);

		List<User> resultList = this.controller.findAll();

		Assertions.assertEquals(2, resultList.size());

	}
	
	@Test
	void testDelete() throws Exception {

		Mockito.doNothing().when(service).delete(user.getUsername());

		this.controller.delete(user.getUsername());

		Mockito.verify(this.service, Mockito.times(1)).delete(user.getUsername());
	}

}
