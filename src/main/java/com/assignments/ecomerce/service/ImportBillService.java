package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.*;
import com.assignments.ecomerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public interface ImportBillService {
    ImportBill save(ImportBill importBill);

    ImportBill findById(Integer id);

    List<ImportBill> getAllImportBill() ;

    ImportBill getImportBillById(Integer id);

    int countImportBill();

    Page<ImportBill> pageImportBill(int pageNo);

//    Page<ImportBill> pageImportBillById(int pageNo,Integer userId);

    Page<ImportBill> searchImportBill(int pageNo, String keyword);
    Page toPage(List<ImportBill> list, Pageable pageable);
    List<ImportBill> transfer(List<ImportBill> importBills);
    Page<ImportBill> searchImportBillByTime(int pageNo, Date dateFrom, Date dateTo);

}
