package com.fresco.ecommerce.service;

import java.util.Optional;

import com.fresco.ecommerce.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fresco.ecommerce.repo.UserRepo;

@Service
public class UserAuthService implements UserDetailsService {
	@Autowired
	private UserRepo userRepo;

	public UserEntity loadUserByUserID(Integer id) {
		Optional<UserEntity> user = userRepo.findById(id);
		if (user.isPresent())
			return user.get();
		else
			throw new UsernameNotFoundException("UserEntity ID not found");
	}

	@Override
	public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> user = userRepo.findByUsername(username);
		if (user.isPresent())
			return user.get();
		else
			throw new UsernameNotFoundException("UserEntity ID not found");
	}
}
