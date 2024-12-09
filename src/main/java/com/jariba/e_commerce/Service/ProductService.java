package com.jariba.e_commerce.Service;


import com.jariba.e_commerce.Model.Product;
import com.jariba.e_commerce.Model.ProductImage;
import com.jariba.e_commerce.Repo.ProductImageRepo;
import com.jariba.e_commerce.Repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductImageRepo productImageRepo;
    @Autowired
    private ProductRepo productRepo;

    public List<Product> getProducts() {

        return productRepo.findAll();
    }

    public Product getProductsById(int productId) {
        return productRepo.findById(productId).orElse(null);
    }

//    public Product addProduct(Product product, MultipartFile img) throws IOException {
//        product.setImageName(img.getOriginalFilename());
//        product.setImageType(img.getContentType());
//        product.setImageData(img.getBytes());
//        return productRepo.save(product);
//    }

    public Product addProduct(Product product) throws IOException {
//        product.setImageName(img.getOriginalFilename());
//        product.setImageType(img.getContentType());
//        product.setImageData(img.getBytes());
        return productRepo.save(product);
    }

    public void addProductImage(MultipartFile img, int productId) throws Exception{

        ProductImage p = new ProductImage();
        Product product = productRepo.findById(productId).orElse(null);
        if(product == null) {
            throw new IllegalArgumentException("ProductId is Invalid");
        }

        p.setImageName(img.getOriginalFilename());
        p.setImageType(img.getContentType());
        p.setProductImage(img.getBytes());
        p.setProduct(product);
        productImageRepo.save(p);
    }

    public void deletProduct(int productId) {
        productRepo.deleteById(productId);
    }

    public List<Product> searchProduct(String keyword) {
        System.out.println("Searching with the Keyword: " + keyword);
        return productRepo.searchProduct(keyword);
    }

    public void updateProduct(Product product) throws  Exception
    {
        try {
            productRepo.save(product);
        }
        catch (Exception e) {
            throw new Exception("Product updation Failed");
        }
    }

    public List<ProductImage> getProductImage(int productId) {

        List<ProductImage> productImage;
        productImage = productImageRepo.findByProductId(productId);
        return productImage;
    }
}
