package com.fresco.ecommerce.repo;

import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fresco.ecommerce.models.CartProduct;

@Repository
public interface CartProductRepo extends JpaRepository<CartProduct, Integer> {
	Optional<CartProduct> findByCartUserEntityUserIdAndProductProductId(Integer userId, Integer productId);

	@Transactional
	void deleteByCartUserEntityUserIdAndProductProductId(Integer userId, Integer productId);
}