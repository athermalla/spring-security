package com.fresco.ecommerce.controllers;

import java.util.List;

import com.fresco.ecommerce.config.JwtUtil;
import com.fresco.ecommerce.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fresco.ecommerce.models.Product;
import com.fresco.ecommerce.repo.ProductRepo;

@RestController
@RequestMapping("/api/public")
public class PublicController {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private ProductRepo productRepo;


	@GetMapping("/product/search")
	public List<Product> getProducts(@RequestParam String keyword) {
		List<Product> productList = productRepo.findByProductNameContainingIgnoreCaseOrCategoryCategoryNameContainingIgnoreCase(keyword, keyword);
		return productList;
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserEntity userEntity) {
		
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(userEntity.getUsername(), userEntity.getPassword()));
		if (authentication.isAuthenticated()) {
			return  new ResponseEntity<>(jwtUtil.createToken(userEntity.getUsername()),HttpStatus.OK) ;
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

}