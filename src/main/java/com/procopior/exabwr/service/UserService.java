package com.procopior.exabwr.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.procopior.exabwr.dto.UserDTO;
import com.procopior.exabwr.exception.UserException;
import com.procopior.exabwr.model.User;
import com.procopior.exabwr.repository.UserRepository;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository repository;

	@Override
	public UserDTO save(final UserDTO dto) throws UserException {
		Optional<User> usr = this.repository.findByUsername(dto.getUsername());
		if (usr.isPresent()) {
			throw new UserException("Username exists");
		}
		usr = this.repository.findByEmail(dto.getEmail());
		if (usr.isPresent()) {
			throw new UserException("Email exists");
		}
		User newUser = new User();
		BeanUtils.copyProperties(dto, newUser);
		this.repository.save(newUser);
		return dto;
	}

	@Override
	public List<User> findAll() {
		return this.repository.findAll();
	}

	@Override
	public UserDTO update(final UserDTO dto) throws UserException {
		User usr = this.repository.findByUsername(dto.getUsername())
				.orElseThrow(() -> new UserException("User not found"));
		BeanUtils.copyProperties(dto, usr);
		this.repository.save(usr);
		return dto;
	}

	@Override
	public void delete(final String username) throws UserException {
		repository.findByUsername(username).orElseThrow(() -> new UserException("User not found"));
		this.repository.deleteById(username);
	}

}
