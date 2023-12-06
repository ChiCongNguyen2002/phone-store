package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.*;
import com.assignments.ecomerce.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
public class ImportBillController {
    @Autowired
    private ImportBillService ImportService;

    @Autowired
    private ImportBillDetailService importBillDetailService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/import/{pageNo}")
    public String getAllImport(@PathVariable("pageNo") int pageNo, Model model, Principal principal) {
        Page<ImportBill> listImport = ImportService.pageImportBill(pageNo);
        List<String> formattedPrices = new ArrayList<>();
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.getDefault());
        decimalFormatSymbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,###", decimalFormatSymbols);

        for (ImportBill importBill : listImport) {
            String formattedPrice = decimalFormat.format(importBill.getTotal());
            formattedPrices.add(formattedPrice);
        }
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        model.addAttribute("suppliers", suppliers);
        model.addAttribute("formattedPrices", formattedPrices);
        model.addAttribute("listImport", listImport);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listImport.getTotalPages());
        model.addAttribute("newImportBill", new ImportBill());
        return "importBill";
    }

    @GetMapping("/search-import/{pageNo}")
    public String searchImport(@PathVariable("pageNo") int pageNo,
                               @RequestParam("keyword") String keyword,
                               Model model, Principal principal) {
        Page<ImportBill> listImport = ImportService.searchImportBill(pageNo, keyword);
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        model.addAttribute("suppliers", suppliers);

        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        model.addAttribute("size", listImport.getSize());
        model.addAttribute("listImport", listImport);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listImport.getTotalPages());
        model.addAttribute("newImportBill", new ImportBill());
        return "importBill";
    }

    @GetMapping("/getProducts")
    @ResponseBody
    public List<Product> getProducts(@RequestParam("categoryId") Integer categoryId) {
        List<Product> productList = productService.findAllByCategoryId(categoryId);
        return productList;
    }

    @GetMapping("/getProductColors")
    @ResponseBody
    public List<Product> getProductColors(@RequestParam("productName") String productName) {
        List<Product> colorList_imageList = productService.getListColorByNameProduct(productName);
        return colorList_imageList;
    }

    @GetMapping("/getProductImage")
    @ResponseBody
    public String getProductImage(@RequestParam("productId") Integer productId) {
        Product product = productService.getById(productId);
        return product.getImage();
    }

    @GetMapping("/add-import")
    public String addImportBill(@RequestParam("supplierId") Integer supplierId,
                                @RequestParam("idList") String idList,
                                @RequestParam("priceList") String priceList,
                                @RequestParam("quantityList") String quantityList,
                                @RequestParam("amountList") String amountList,
                                @RequestParam("totalAmount") Double totalAmount,
                                RedirectAttributes redirectAttributes) {

        Date date = new Date();
        Supplier supplier = supplierService.getById(supplierId);

        ImportBill importBill = new ImportBill();
        importBill.setSupplier(supplier);
        importBill.setImportDate(date);
        importBill.setTotal(totalAmount);
        importBill.setStatus(1);

        ImportBill newSave = ImportService.save(importBill);
        System.out.print(newSave.getId());

        String[] id = idList.split(" ");
        String[] price = priceList.split(" ");
        String[] quantity = quantityList.split(" ");
        String[] amount = amountList.split(" ");
        System.out.println(totalAmount);

        for (int i = 0; i < id.length; i++) {
            ImportBillDetail detail = new ImportBillDetail();
            detail.setImportBill(newSave);

            Product product = productService.getProductById(Integer.parseInt(id[i]));
            detail.setProduct(product);
            product.setQuantity(product.getQuantity() + Integer.parseInt(quantity[i]));

            detail.setQuantity(Integer.parseInt(quantity[i]));
            detail.setUnitPrice(Double.parseDouble(price[i]));
            importBillDetailService.save(detail);
        }
        redirectAttributes.addFlashAttribute("successImportMessage", "Import bill has been added successfully!");
        return "redirect:/search-import/0?keyword=";
    }

    @GetMapping("/search-import-by-time/{pageNo}")
    public String searchOrderByTime(@PathVariable("pageNo") int pageNo,
                                    @RequestParam("dateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
                                    @RequestParam("dateTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo,
                                    Model model, Principal principal) {
        Page<ImportBill> listImport = ImportService.searchImportBillByTime(pageNo, dateFrom,dateTo);
        List<String> formattedPrices = new ArrayList<>();
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.getDefault());
        decimalFormatSymbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,###", decimalFormatSymbols);

        for (ImportBill importBill : listImport) {
            String formattedPrice = decimalFormat.format(importBill.getTotal());
            formattedPrices.add(formattedPrice);
        }
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        model.addAttribute("suppliers", suppliers);

        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);

        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        model.addAttribute("formattedPrices", formattedPrices);
        model.addAttribute("size", listImport.getSize());
        model.addAttribute("listImport", listImport);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listImport.getTotalPages());
        model.addAttribute("newImportBill", new ImportBill());
        return "importBill";
    }
}