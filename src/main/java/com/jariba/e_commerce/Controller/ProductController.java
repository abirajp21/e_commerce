package com.jariba.e_commerce.Controller;


import com.jariba.e_commerce.Model.Product;
import com.jariba.e_commerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ProductController {


    @Autowired
    private ProductService service;


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts()
    {
        return new ResponseEntity<>(service.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProductsById(@PathVariable int productId)
    {
        Product product = service.getProductsById(productId);
        if(product != null)
            return new ResponseEntity<>(product, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }





}
