package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.ImportBillDetail;
import com.assignments.ecomerce.model.OrderDetail;
import com.assignments.ecomerce.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ImportBillDetailService {

    ImportBillDetail save(ImportBillDetail importBill);
    List<ImportBillDetail> getAllImportBillDetail();

    List<ImportBillDetail> findAllByImportBillId(Integer importBillId) ;
}
