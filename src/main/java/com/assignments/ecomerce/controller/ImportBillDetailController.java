package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.*;
import com.assignments.ecomerce.service.ImportBillDetailService;
import com.assignments.ecomerce.service.ImportBillService;
import com.assignments.ecomerce.service.OrderDetailService;
import com.assignments.ecomerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class ImportBillDetailController {
    @Autowired
    private ImportBillDetailService importBillDetailService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private ImportBillService importBillService;
    @PostMapping("/import-bill-detail")
    public String getAllOrderDetail(@RequestParam("importId") Integer importId, Model model, Principal principal) {
        List<ImportBill> importBillList = new ArrayList<>();
        ImportBill importBill = importBillService.getImportBillById(importId);
        if (importBill != null) {
            importBillList.add(importBill);
        }
        model.addAttribute("listImport", importBillList);
        List<ImportBillDetail> importBillDetailList = importBillDetailService.findAllByImportBillId(importId);

        List<String> formattedPrices = new ArrayList<>();
        List<String> formattedPriceDetail = new ArrayList<>();
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.getDefault());
        decimalFormatSymbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,###", decimalFormatSymbols);

        for (ImportBill importBill1 : importBillList) {
            String formattedPrice = decimalFormat.format(importBill1.getTotal());
            formattedPrices.add(formattedPrice);
        }

        for (ImportBillDetail importBillDetail : importBillDetailList) {
            String formattedPrice = decimalFormat.format(importBillDetail.getUnitPrice());
            formattedPriceDetail.add(formattedPrice);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        model.addAttribute("formattedPrices", formattedPrices);
        model.addAttribute("formattedPriceDetail", formattedPriceDetail);
        model.addAttribute("listImportBillDetail", importBillDetailList);
        return "importBillDetail";
    }
}
