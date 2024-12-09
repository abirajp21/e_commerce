package com.jariba.e_commerce.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  int imageId;

    @NotEmpty
    private String imageName;

    @NotEmpty
    private String imageType;
    @Lob
    private byte[] productImage;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "productId", referencedColumnName = "id")
    @JsonIgnore
    private Product product;

}
