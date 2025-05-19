package com.fresco.ecommerce.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fresco.ecommerce.models.Role;
import com.fresco.ecommerce.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepo.findByUsername(username).orElseThrow();

		List<GrantedAuthority> authorityList = buildAuth(user.getRoles());

		return new User(user.getUsername(), user.getPassword(), authorityList);
	}

	private List<GrantedAuthority> buildAuth(Set<Role> roles) {

		return roles.stream()
				.map(x-> new SimpleGrantedAuthority(x.toString()))
				.collect(Collectors.toList());
	}
}
