package com.jariba.e_commerce.Repo;

import com.jariba.e_commerce.Model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepo extends JpaRepository<ProductImage, Integer> {


    List<ProductImage> findByProductId(int productId);
}
