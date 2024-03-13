package com.curso.mycommerce.services;

import com.curso.mycommerce.dto.ProductDTO;
import com.curso.mycommerce.entities.Product;
import com.curso.mycommerce.repositories.ProductRepository;
import com.curso.mycommerce.services.exception.DatabaseException;
import com.curso.mycommerce.services.exception.ExceptionNotFound;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.AccessOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ExceptionNotFound("Produto não encontrado."));
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
       try {
           Product product = productRepository.getReferenceById(id);
           copyDtoForProduct(dto,product);
           product = productRepository.save(product);
           return new ProductDTO(product);
       }
       catch (EntityNotFoundException e){
           throw new ExceptionNotFound("Id not Found " + id);
       }
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id){
        try {
            productRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e){
            throw new ExceptionNotFound("Id not Found " + id);
        }
        catch (DataIntegrityViolationException e){
            throw  new DatabaseException("Existe uma chave estrangeira.");
        }
    }

    private void copyDtoForProduct(ProductDTO dto, Product product) {
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImgUrl(dto.getImgUrl());
    }

}
