package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.ImportBill;
import com.assignments.ecomerce.model.ImportBillDetail;
import com.assignments.ecomerce.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImportBillDetailRepository extends JpaRepository<ImportBillDetail, Integer> {
    List<ImportBillDetail> findAllByImportBillId(Integer importBillId);

}
