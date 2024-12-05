package com.jariba.e_commerce.Controller;


import com.jariba.e_commerce.Model.Product;
import com.jariba.e_commerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("products/{productId}/image")
    public ResponseEntity<byte[]> getProductImageById(@PathVariable int productId)
    {
        Product product = service.getProductsById(productId);
        if(product != null)
            return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(product.getImageData());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }




//    @PostMapping(value =  "/products", consumes = {"multipart/form-data", "pplication/octet-stream"})
//   // @PostMapping("/products")
//    public ResponseEntity<Product> addProduct(@RequestPart("product") Product product,@RequestPart("image") MultipartFile img)
//    {
//        System.out.println("Hello");
//
//        try {
//            Product p1 = service.addProduct(product, img);
//            return new ResponseEntity<>(p1,HttpStatus.CREATED);
//        }
//        catch (Exception e)
//        {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }

    @PostMapping(value = "/products")
    public ResponseEntity<Product> addProduct(@RequestBody Product product)
    {
        try {
            Product p = service.addProduct(product);
            return new ResponseEntity<>(p,HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PutMapping("/products")
    public ResponseEntity<?> updateProduct(@RequestBody Product product )
    {
        String response = service.updateProduct(product);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/products/{productId}/image")
    public ResponseEntity<?> updateProductImage(MultipartFile image, @PathVariable int productId)
    {
        try {
            service.addimage(image, productId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Product Not found",HttpStatus.NOT_FOUND);
        }

    }



    @PostMapping(value = "/products/{productId}/image")
    public ResponseEntity<?> addProductImage(MultipartFile image, @PathVariable int productId)
    {
        try {
            service.addimage(image, productId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable int productId)
    {
        service.deletProduct(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword)
    {
        List<Product> products = service.searchProduct(keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);

    }


//    @PutMapping("/products")
//    public ResponseEntity<?> updateProduct(@RequestBody Product product)

}
