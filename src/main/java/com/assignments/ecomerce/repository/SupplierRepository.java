package com.assignments.ecomerce.repository;
import com.assignments.ecomerce.model.Category;
import com.assignments.ecomerce.model.Coupon;
import com.assignments.ecomerce.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Integer> {
    @Query("SELECT p from Supplier p where status = 1")
    Page<Supplier> pageSupplier(Pageable pageable);

    @Query("SELECT p from Supplier p where p.status = 1 and CONCAT(p.name,p.phoneNumber,p.address) like %?1%")
    List<Supplier> searchSupplier(String keyword);

    @Query(value = "select * from Supplier where status = 1", nativeQuery = true)
    List<Supplier> findByStatusActivated();

    @Query(value = "SELECT s FROM Supplier s WHERE s.status = 1 and s.name = ?1 OR s.phoneNumber = ?2")
    Supplier findByNameOrPhoneNumber(String name, String phoneNumber);

    @Query(value = "SELECT s FROM Supplier s WHERE s.status = 1 and s.name = ?1")
    Supplier findByName(String name);
}

