package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.Orders;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public interface ExcelExportService {
    public void exportToExcel(List<Orders> orders, String filePath) throws IOException;
}
