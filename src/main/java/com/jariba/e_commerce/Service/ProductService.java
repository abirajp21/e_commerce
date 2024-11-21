package com.jariba.e_commerce.Service;


import com.jariba.e_commerce.Model.Product;
import com.jariba.e_commerce.Repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public Product addProduct(Product product, MultipartFile img) throws IOException {
        product.setImageName(img.getOriginalFilename());
        product.setImageType(img.getContentType());
        product.setImageData(img.getBytes());
        return repo.save(product);
    }

    public Product addProduct(Product product) throws IOException {
//        product.setImageName(img.getOriginalFilename());
//        product.setImageType(img.getContentType());
//        product.setImageData(img.getBytes());
        return repo.save(product);
    }

    public void addimage(MultipartFile img, int productId) throws  IOException{
        Product p = repo.findById(productId).orElse(null);

        if(p == null)
            throw new IOException();

        p.setImageName(img.getOriginalFilename());
        p.setImageType(img.getContentType());
        p.setImageData(img.getBytes());

        repo.save(p);
    }

    public void deletProduct(int productId) {
        repo.deleteById(productId);
    }
}
