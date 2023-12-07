package com.assignments.ecomerce.service.impl;

import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.repository.ProductRepository;
import com.assignments.ecomerce.service.ProductService;
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
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product findById(Integer id) {
        return productRepository.findById(id).get();
    }

    public List<Product> getListColorByNameProduct(String name) {
        List<Object[]> resultProduct = productRepository.getListColorByNameProduct(name);
        List<Product> products = new ArrayList<>();

        for (Object[] result : resultProduct) {
            String color = (String) result[0];
            String image = (String) result[1];
            Integer id = (Integer) result[2];
            Product product = new Product(color, image);
            product.setId(id);
            products.add(product);
        }
        return products;
    }

    public List<Product> getTopSellingProducts() {
        return productRepository.findTop10ByQuantitySold();
    }

    public List<Product> findAllByCategory(String category) {
        return transfer(productRepository.findAllByCategory(category));
    }

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<Product> productList = transfer(products);
        return productList;
    }

    public Product save(MultipartFile photo, Product product) {
        try {
            Product newProduct = new Product();
            Path uploadPath = Paths.get("src", "main", "resources", "static", "img");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String photoFileName = StringUtils.cleanPath(photo.getOriginalFilename() != null ? photo.getOriginalFilename() : "unknown_photo_file");
            Path photoTargetPath = uploadPath.resolve(photoFileName);
            Files.copy(photo.getInputStream(), photoTargetPath, StandardCopyOption.REPLACE_EXISTING);

            if (!photo.isEmpty()) {
                newProduct.setImage(photoFileName);
            } else {
                newProduct.setImage(photoFileName);
            }

            newProduct.setName(product.getName());
            newProduct.setDescription(product.getDescription());
            newProduct.setCategory(product.getCategory());
            newProduct.setColor(product.getColor());
            newProduct.setPrice(0);
            newProduct.setQuantity(0);
            newProduct.setScreenSize(product.getScreenSize());
            newProduct.setFrontCamera(product.getFrontCamera());
            newProduct.setChipset(product.getChipset());
            newProduct.setInternalStorage(product.getInternalStorage());
            newProduct.setBattery(product.getBattery());
            newProduct.setStatus(1);
            productRepository.save(newProduct);
            return newProduct;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Product getById(Integer id) {
        Product product = productRepository.getById(id);
        Product newProduct = new Product();
        newProduct.setId(product.getId());
        newProduct.setName(product.getName());
        newProduct.setDescription(product.getDescription());
        newProduct.setCategory(product.getCategory());
        newProduct.setPrice(product.getPrice());
        newProduct.setQuantity(product.getQuantity());
        newProduct.setImage(product.getImage());
        newProduct.setScreenSize(product.getScreenSize());
        newProduct.setFrontCamera(product.getFrontCamera());
        newProduct.setChipset(product.getChipset());
        newProduct.setInternalStorage(product.getInternalStorage());
        newProduct.setBattery(product.getBattery());
        return product;
    }

    public Product getProductByColorAndName(String name, String color) {
        return productRepository.getProductByColorAndName(name, color);
    }

    public Product getProductById(Integer id) {
        Optional<Product> optionalOrder = productRepository.findById(id);
        return optionalOrder.orElse(null);
    }

    public Product update(MultipartFile photo, Product product) {
        try {
            Path uploadPath = Paths.get("src", "main", "resources", "static", "img");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String photoFileName = StringUtils.cleanPath(photo.getOriginalFilename() != null ? photo.getOriginalFilename() : "unknown_photo_file");
            Path photoTargetPath = uploadPath.resolve(photoFileName);
            Product productUpdate = productRepository.getById(product.getId());

            String productImage = productUpdate.getImage();
            if (!photo.isEmpty()) {
                productUpdate.setImage(photoFileName);
            } else {
                productUpdate.setImage(productImage);
            }

            productUpdate.setName(product.getName());
            productUpdate.setPrice(product.getPrice());
            productUpdate.setDescription(product.getDescription());
            productUpdate.setCategory(product.getCategory());
            productUpdate.setQuantity(product.getQuantity());
            productUpdate.setColor(product.getColor());
            productUpdate.setScreenSize(product.getScreenSize());
            productUpdate.setFrontCamera(product.getFrontCamera());
            productUpdate.setChipset(product.getChipset());
            productUpdate.setInternalStorage(product.getInternalStorage());
            productUpdate.setBattery(product.getBattery());
            productRepository.save(productUpdate);
            return productUpdate;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public int countProducts() {
        return productRepository.countProducts();
    }

    public Double getTotalRevenue() {
        return productRepository.getTotalRevenue();
    }

    @Override
    public List<Product> findAllByCategoryId(Integer categoryId) {
        return transfer(productRepository.findAllByCategoryId(categoryId));
    }

    public Page<Product> pageProducts(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 3);
        return productRepository.pageProduct(pageable);
    }

    public Page<Product> pageProductByCategory(int pageNo, Integer categoryId) {
        Pageable pageable = PageRequest.of(pageNo, 4);
        return productRepository.pageProductByCategory(pageable, categoryId);
    }

    @Override
    public Page<Product> findProductByAdmin(int pageNo, String keyword) {
        Pageable pageable = PageRequest.of(pageNo, 4);
        List<Product> products = transfer(productRepository.findProductByAdmin(keyword.trim()));
        Page<Product> productPages = toPage(products, pageable);
        return productPages;
    }

    public Page<Product> searchProducts(int pageNo, String keyword) {
        Pageable pageable = PageRequest.of(pageNo, 4);
        List<Product> products = transfer(productRepository.findProductsByKeywordWithMinMaxPrice(keyword.trim()));
        Page<Product> productPages = toPage(products, pageable);
        return productPages;
    }

    public Page<Product> searchProductByOption(int pageNo, String category, String sortOption, String color, Double minPrice, Double maxPrice) {
        Pageable pageable = PageRequest.of(pageNo, 4);
        List<Product> products;

        if ("ASC".equals(sortOption)) {
            products = transfer(productRepository.searchProductByOptionAscending(category, color, minPrice, maxPrice));
        } else {
            products = transfer(productRepository.searchProductByOptionDescending(category, color, minPrice, maxPrice));
        }
        Page<Product> productPages = toPage(products, pageable);
        return productPages;
    }

    public Page toPage(List<Product> list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

    public List<Product> transfer(List<Product> products) {
        List<Product> productList = new ArrayList<>();
        for (Product product : products) {
            Product newProduct = new Product();
            newProduct.setId(product.getId());
            newProduct.setName(product.getName());
            newProduct.setDescription(product.getDescription());
            newProduct.setCategory(product.getCategory());
            newProduct.setPrice(product.getPrice());
            newProduct.setColor(product.getColor());
            newProduct.setQuantity(product.getQuantity());
            newProduct.setImage(product.getImage());
            newProduct.setScreenSize(product.getScreenSize());
            newProduct.setFrontCamera(product.getFrontCamera());
            newProduct.setChipset(product.getChipset());
            newProduct.setInternalStorage(product.getInternalStorage());
            newProduct.setBattery(product.getBattery());
            productList.add(newProduct);
        }
        return productList;
    }

    public void deleteById(Integer id) {
        Product product = productRepository.getById(id);
        product.setStatus(0);
        productRepository.save(product);
    }

    public void enableById(Integer id) {
        Product product = productRepository.getById(id);
        productRepository.save(product);
    }

    public List<Product> getData() {
        List<Object[]> resultProduct = productRepository.getTop10ProductsWithSumQuantity();
        List<Product> products = new ArrayList<>();

        for (Object[] result : resultProduct) {
            Integer id = (Integer) result[0];
            String name = (String) result[1];
            Integer price = (Integer) result[2];
            String description = (String) result[3];
            Integer quantity = (Integer) result[4];
            String color = (String) result[5];
            String image = (String) result[6];

            Product product = new Product(id, name, price, quantity, description, color, image);
            products.add(product);
        }
        return products;
    }

    @Override
    public double getMinPrice(Page<Product> productList) {
        double minPrice = Double.MAX_VALUE;
        for (Product product : productList) {
            if (product.getPrice() <= minPrice) {
                minPrice = product.getPrice();
            }
        }
        return minPrice;
    }

    @Override
    public double getMaxPrice(Page<Product> productList) {
        double maxPrice = Double.MIN_VALUE;
        for (Product product : productList) {
            if (product.getPrice() >= maxPrice) {
                maxPrice = product.getPrice();
            }
        }
        return maxPrice;
    }
}
