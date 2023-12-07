package com.assignments.ecomerce.service.impl;

import com.assignments.ecomerce.model.*;
import com.assignments.ecomerce.repository.ImportBillRepository;
import com.assignments.ecomerce.service.ImportBillService;
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
public class ImportBillServiceImpl implements ImportBillService {
    @Autowired
    private ImportBillRepository importBillRepository;

    @Override
    public ImportBill save(ImportBill ImportBill) {
        return importBillRepository.save(ImportBill);
    }

    public ImportBill findById(Integer id) {
        return importBillRepository.findById(id).get();
    }

    public List<ImportBill> getAllImportBill() {
        return (List<ImportBill>) importBillRepository.findAll();
    }

    public ImportBill getImportBillById(Integer id) {
        Optional<ImportBill> optionalOrder = importBillRepository.findById(id);
        return optionalOrder.orElse(null);
    }

    public int countImportBill() {
        return importBillRepository.countImportBill();
    }

    public Page<ImportBill> pageImportBill(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        return importBillRepository.pageImportBill(pageable);
    }

    public Page<ImportBill> searchImportBill(int pageNo, String keyword) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<ImportBill> importBills = transfer(importBillRepository.searchImportBill(keyword));
        Page<ImportBill> importBillPage = toPage(importBills, pageable);
        return importBillPage;
    }

    public Page toPage(List<ImportBill> list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

    public List<ImportBill> transfer(List<ImportBill> importBills) {
        List<ImportBill> importBillList = new ArrayList<>();
        for (ImportBill importBill : importBills) {
            ImportBill newImport = new ImportBill();
            newImport.setId(importBill.getId());
            newImport.setSupplier(importBill.getSupplier());
            newImport.setImportDate(importBill.getImportDate());
            newImport.setStatus(importBill.getStatus());
            newImport.setTotal(importBill.getTotal());
            newImport.setImportBillDetails(importBill.getImportBillDetails());
            importBillList.add(newImport);
        }
        return importBillList;
    }

    public Page<ImportBill> searchImportBillByTime(int pageNo, Date dateFrom, Date dateTo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<ImportBill> importBills = transfer(importBillRepository.searchImportBillByTime(dateFrom, dateTo));
        Page<ImportBill> orderPages = toPage(importBills, pageable);
        return orderPages;
    }
}
