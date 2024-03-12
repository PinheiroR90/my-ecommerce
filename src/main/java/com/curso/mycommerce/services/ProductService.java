package com.curso.mycommerce.services;

import com.curso.mycommerce.dto.ProductDTO;
import com.curso.mycommerce.entities.Product;
import com.curso.mycommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Product product = productRepository.findById(id).get();
        return new ProductDTO(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable){
        Page<Product> product = productRepository.findAll(pageable);
        return  product.map(ProductDTO::new);
    }

    @Transactional
    public ProductDTO create(ProductDTO dto){
        Product product = new Product();
        copyDtoForProduct(dto,product);
        product = productRepository.save(product);
        return new ProductDTO(product);
    }
    @Transactional
    public ProductDTO update(Long id, ProductDTO dto){
        Product product = productRepository.getReferenceById(id);
        copyDtoForProduct(dto,product);
        product = productRepository.save(product);
        return new ProductDTO(product);
    }

    public ProductDTO

    @Transactional
    private void copyDtoForProduct(ProductDTO dto, Product product) {
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImgUrl(dto.getImgUrl());
    }

}
