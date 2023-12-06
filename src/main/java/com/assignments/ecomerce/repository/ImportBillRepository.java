package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.ImportBill;
import com.assignments.ecomerce.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ImportBillRepository extends JpaRepository<ImportBill, Integer> {
    @Query(value = "SELECT COUNT(*) FROM ImportBill", nativeQuery = true)
    int countImportBill();

    @Query("SELECT i from ImportBill i WHERE i.importDate BETWEEN :dateFrom AND :dateTo ")
    List<ImportBill> searchImportBillByTime(@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);

    @Query("SELECT i from ImportBill i")
    Page<ImportBill> pageImportBill(Pageable pageable);

    @Query("SELECT i from ImportBill i where CONCAT(i.importDate,i.status,i.total) like %?1%")
    List<ImportBill> searchImportBill(String keyword);

}
