package com.assignments.ecomerce.service.impl;

import com.assignments.ecomerce.model.Orders;
import com.assignments.ecomerce.service.ExcelExportService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelExportServiceImpl implements ExcelExportService {

    public void exportToExcel(List<Orders> orders, String filePath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Order Data");

        CellStyle dateCellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd")); // You can change the format as needed


        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Date");
        headerRow.createCell(2).setCellValue("Total");
        headerRow.createCell(3).setCellValue("Name");

        // Populate data rows
        int rowNum = 1;
        for (Orders order : orders) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(order.getId());
            row.createCell(1).setCellValue(order.getOrderDate());
            row.getCell(1).setCellStyle(dateCellStyle);

            row.createCell(2).setCellValue(order.getTotal());
            row.createCell(3).setCellValue(order.getUser().getFullname());
        }
        sheet.autoSizeColumn(1); // Adjust the column index as needed


        // Write to file
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }

        // Close the workbook to release resources
        workbook.close();
    }
}
