package com.assignments.ecomerce.controller;
import com.assignments.ecomerce.model.Orders;
import com.assignments.ecomerce.service.ExcelExportService;
import com.assignments.ecomerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Controller
public class ExcelController {

    private final ExcelExportService excelExportService;
    @Autowired
    private OrderService orderService;

    public ExcelController(ExcelExportService excelExportService) {
        this.excelExportService = excelExportService;
    }

    @GetMapping("/export/orders-excel/{pageNo}")
    public String exportOrdersToExcel(@PathVariable("pageNo") int pageNo,
                                        @RequestParam("dateFromExcel") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
                                      @RequestParam("dateToExcel") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo,
                                      Model model) {
        List<Orders> list = orderService.searchOrdersByTimeToExcel(dateFrom,dateTo);
        try {
            Path outputPath = Paths.get("src", "main", "resources", "static", "excel", "file.xlsx");
            if (!Files.exists(outputPath)) {
                Files.createDirectories(outputPath);
            }


            excelExportService.exportToExcel(list, "src/main/resources/static/excel/file.xlsx");
            Page<Orders> listOrder = orderService.searchOrdersByTime(pageNo, dateFrom,dateTo);
            model.addAttribute("size", listOrder.getSize());
            model.addAttribute("listOrder", listOrder);
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("totalPages", listOrder.getTotalPages());
            model.addAttribute("successMessage", "Export Excel Success");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "order";
    }
}
