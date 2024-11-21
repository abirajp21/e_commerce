package com.jariba.e_commerce.Service;


import com.jariba.e_commerce.Model.Product;
import com.jariba.e_commerce.Repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;
    public List<Product> getProducts() {

        return repo.findAll();

    }

    public Product getProductsById(int productId) {
        return repo.findById(productId).orElse(null);
    }
}
