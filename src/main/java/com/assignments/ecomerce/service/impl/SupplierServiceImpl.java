
package com.assignments.ecomerce.service.impl;

import com.assignments.ecomerce.model.Coupon;
import com.assignments.ecomerce.model.Supplier;
import com.assignments.ecomerce.repository.SupplierRepository;
import com.assignments.ecomerce.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private  SupplierRepository supplierRepository;

    public Supplier save(Supplier supplier) {
        supplier.setStatus(1);
        return supplierRepository.save(supplier);
    }

    public Page<Supplier> pageSupplier(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 4);
        return supplierRepository.pageSupplier(pageable);
    }

    public Supplier findById(Integer id) {
        return supplierRepository.findById(id).get();
    }

    public Supplier update(Supplier supplier) {
        Supplier supplierUpdate = null;
        try {
            supplierUpdate = supplierRepository.findById(supplier.getId()).get();
            supplierUpdate.setName(supplier.getName());
            supplierUpdate.setPhoneNumber(supplier.getPhoneNumber());
            supplierUpdate.setAddress(supplier.getAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return supplierRepository.save(supplierUpdate);
    }

    public void deleteById(Integer id) {
        Supplier supplier = supplierRepository.getById(id);
        supplier.setStatus(0);
        supplierRepository.save(supplier);
    }

    public Page<Supplier> searchSuppliers(int pageNo, String keyword) {
        Pageable pageable = PageRequest.of(pageNo, 4);
        List<Supplier> supplier = transfer(supplierRepository.searchSupplier(keyword.trim()));
        Page<Supplier> supplierPages = toPage(supplier, pageable);
        return supplierPages;
    }

    public Page toPage(List<Supplier> list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

    public List<Supplier> transfer(List<Supplier> suppliers) {
        List<Supplier> supplierList = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            Supplier newSupplier = new Supplier();
            newSupplier.setId(supplier.getId());
            newSupplier.setName(supplier.getName());
            newSupplier.setPhoneNumber(supplier.getPhoneNumber());
            newSupplier.setAddress(supplier.getAddress());
            supplierList.add(newSupplier);
        }
        return supplierList;
    }

    @Override
    public Supplier findByNameOrPhoneNumber(String name,String phoneNumber) {
        return supplierRepository.findByNameOrPhoneNumber(name,phoneNumber);
    }

    @Override
    public Supplier findByName(String name) {
        return supplierRepository.findByName(name);
    }

    @Override
    public Supplier updateStatus(Integer id) {
        Supplier supplierUpdate = supplierRepository.getById(id);
        supplierUpdate.setStatus(1);
        return supplierRepository.save(supplierUpdate);
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findByStatusActivated();
    }
}

