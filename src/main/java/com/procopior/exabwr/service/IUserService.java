package com.procopior.exabwr.service;

import java.util.List;

import com.procopior.exabwr.dto.UserDTO;
import com.procopior.exabwr.exception.UserException;
import com.procopior.exabwr.model.User;

public interface IUserService {

	UserDTO save(final UserDTO dto) throws UserException;

	List<User> findAll();

	UserDTO update(final UserDTO dto) throws UserException;
	
	void delete(final String username) throws UserException;
}
