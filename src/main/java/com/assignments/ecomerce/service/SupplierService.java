
package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.Coupon;
import com.assignments.ecomerce.model.Supplier;
import com.assignments.ecomerce.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface SupplierService {

     List<Supplier> getAllSuppliers();

     Supplier save(Supplier supplier);

     Page<Supplier> pageSupplier(int pageNo);

     Supplier findById(Integer id);
     Supplier update(Supplier supplier);
     void deleteById(Integer id);
     Page<Supplier> searchSuppliers(int pageNo, String keyword);
    Page toPage(List<Supplier> list, Pageable pageable);
     List<Supplier> transfer(List<Supplier> suppliers);
    Supplier findByNameOrPhoneNumber(String name,String phoneNumber);
    Supplier findByName(String name);
    Supplier updateStatus(Integer id);
}

