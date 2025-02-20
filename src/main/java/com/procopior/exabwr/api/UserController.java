package com.procopior.exabwr.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.procopior.exabwr.dto.UserDTO;
import com.procopior.exabwr.exception.UserException;
import com.procopior.exabwr.model.User;
import com.procopior.exabwr.service.IUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/users")
public class UserController {

	@Autowired
	private IUserService service;

	@PostMapping
	public UserDTO save(@Valid @RequestBody final UserDTO dto) throws UserException {
		return service.save(dto);
	}

	@PatchMapping
	public UserDTO update(@Valid @RequestBody final UserDTO dto) throws UserException {
		return service.update(dto);
	}

	@DeleteMapping("{username}")
	public String delete(@PathVariable("username") final String username) throws UserException {
		service.delete(username);
		return "Delete success";
	}

	@GetMapping
	public List<User> findAll() {
		return service.findAll();
	}

}
