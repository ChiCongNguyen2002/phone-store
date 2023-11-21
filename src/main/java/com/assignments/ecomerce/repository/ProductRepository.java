package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT od.product FROM OrderDetail od GROUP BY od.product.id ORDER BY SUM(od.quantity) DESC")
    List<Product> findTop10ByQuantitySold();

    @Query("SELECT p.color,p.image FROM Product p WHERE p.name = :name")
    List<Object[]> getListColorByNameProduct(@Param("name") String name);

    @Query("SELECT p FROM Product p WHERE p.name = :name AND p.color = :color")
    Product getProductByColorAndName(@Param("name") String name,@Param("color") String color);

    @Query(value = "SELECT COUNT(*) FROM product", nativeQuery = true)
    int countProducts();

    @Query("SELECT SUM(od.quantity * od.unitPrice) FROM OrderDetail od")
    Double getTotalRevenue();

    @Query("SELECT p from Product p where status = 1")
    Page<Product> pageProduct(Pageable pageable);

    @Query("SELECT DISTINCT p FROM Product p JOIN p.category c WHERE p.status = 1 AND c.id = :categoryId GROUP BY p.name")
    Page<Product> pageProductByCategory(Pageable pageable, @Param("categoryId") Integer categoryId);

    @Query("SELECT DISTINCT p FROM Product p JOIN p.category c " +
            "WHERE p.status = 1 AND c.status = 1 " +
            "AND CONCAT(p.name, p.price, p.quantity, p.description, p.color) LIKE %:keyword% GROUP BY p.name")
    List<Product> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT p FROM Product p JOIN p.category c " +
            "WHERE p.status = 1 AND c.status = 1 " +
            "AND c.name LIKE %:category% " +
            "AND p.color LIKE %:color% " +
            "AND p.price >= :minPrice AND p.price <= :maxPrice " +
            "GROUP BY p.name " +
            "ORDER BY p.price ASC")
    List<Product> searchProductByOptionAscending(@Param("category") String category,
                                                 @Param("color") String color,
                                                 @Param("minPrice") Double minPrice,
                                                 @Param("maxPrice") Double maxPrice);

    @Query("SELECT p FROM Product p JOIN p.category c " +
            "WHERE p.status = 1 AND c.status = 1 " +
            "AND c.name LIKE %:category% " +
            "AND p.color LIKE %:color% " +
            "AND p.price >= :minPrice AND p.price <= :maxPrice " +
            "ORDER BY p.price DESC")
    List<Product> searchProductByOptionDescending(@Param("category") String category,
                                                  @Param("color") String color,
                                                  @Param("minPrice") Double minPrice,
                                                  @Param("maxPrice") Double maxPrice);

    @Query("select p from Product p inner join Category c ON c.id = p.category.id" +
            " where p.category.name = ?1")
    List<Product> findAllByCategory(String category);

    @Query("SELECT p.id, p.name, p.price, p.description, p.quantity, p.color,p.image, SUM(od.quantity) AS sumQuantity " +
            "FROM OrderDetail od " +
            "JOIN od.product p " +
            "JOIN od.order o " +
            "GROUP BY p.id,p.name, p.price, p.description, p.quantity, p.color,p.image " +
            "ORDER BY sumQuantity DESC")
    List<Object[]> getTop10ProductsWithSumQuantity();
}
