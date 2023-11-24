package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {
     Product findById(Integer id);
     List<Product> getListColorByNameProduct(String name);

     List<Product> getTopSellingProducts() ;

     List<Product> findAllByCategory(String category) ;

     List<Product> getAllProducts() ;

     Product save(MultipartFile photo, Product product);

     Product getById(Integer id) ;

     Product getProductByColorAndName(String name, String color);;

     Product getProductById(Integer id);

     Product update(MultipartFile photo, Product product);

     int countProducts() ;

     Double getTotalRevenue() ;

     Page<Product> pageProducts(int pageNo);
     Page<Product> pageProductByCategory(int pageNo, Integer categoryId);

     Page<Product> searchProducts(int pageNo, String keyword) ;

     Page<Product> searchProductByOption(int pageNo, String category, String sortOption, String color, Double minPrice, Double maxPrice);
     public Page toPage(List<Product> list, Pageable pageable);

     List<Product> transfer(List<Product> products);

     void deleteById(Integer id);

     void enableById(Integer id) ;

     List<Product> getData();

     public double getMinPrice(Page<Product> productList);

     public double getMaxPrice(Page<Product> productList);
}
