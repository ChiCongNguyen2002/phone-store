package com.assignments.ecomerce.service.impl;

import com.assignments.ecomerce.model.ImportBillDetail;
import com.assignments.ecomerce.model.OrderDetail;
import com.assignments.ecomerce.repository.ImportBillDetailRepository;
import com.assignments.ecomerce.repository.OrderDetailRepository;
import com.assignments.ecomerce.service.ImportBillDetailService;
import com.assignments.ecomerce.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImportBillDetailServiceImpl implements ImportBillDetailService {
    @Autowired
    private ImportBillDetailRepository importBillDetailRepository;

    @Override
    public ImportBillDetail save(ImportBillDetail importBill) {
        return importBillDetailRepository.save(importBill);
    }

    public List<ImportBillDetail> getAllImportBillDetail() {
        return (List<ImportBillDetail>) importBillDetailRepository.findAll();
    }

    public List<ImportBillDetail> findAllByImportBillId(Integer importBillId) {
        return importBillDetailRepository.findAllByImportBillId(importBillId);
    }
}
