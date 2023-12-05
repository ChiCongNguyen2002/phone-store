
package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.Coupon;
import com.assignments.ecomerce.model.Supplier;
import com.assignments.ecomerce.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/supplier/{pageNo}")
    public String getAllSupplier(@PathVariable("pageNo") int pageNo,Model model, Principal principal) {
        Page<Supplier> listSupp = supplierService.pageSupplier(pageNo);
        model.addAttribute("listSupplier", listSupp);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listSupp.getTotalPages());
        model.addAttribute("supplierNew", new Supplier());
        return "supplier";
    }

    @PostMapping("/add-supplier")
    public String add(@ModelAttribute("supplierNew") Supplier supplier, Model model, RedirectAttributes attributes) {
        try {
            Supplier supp = supplierService.findByNameOrPhoneNumber(supplier.getName(), supplier.getPhoneNumber());
            if (supp == null) {
                supplierService.save(supplier);
            }
            else if (supp.getStatus() == 0) {
                supplierService.updateStatus(supp.getId());
            }
            else if (supp.getStatus() == 1) {
                attributes.addFlashAttribute("error", "Tên Nhà Cung Cấp hoặc Số Điện Thoại đã tồn tại. Vui lòng nhập Tên hoặc Số Điện Thoại khác!");
                return "redirect:/search-supplier/0?keyword=";
            }

            model.addAttribute("supplierNew", supplier);
            attributes.addFlashAttribute("success", "Added successfully");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            attributes.addFlashAttribute("failed", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            attributes.addFlashAttribute("failed", "Error Server");
        }
        return "redirect:/search-supplier/0?keyword=";
    }

    @RequestMapping(value = "/findByIdSupplier/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Supplier findById(@PathVariable("id") Integer id){
        return supplierService.findById(id);
    }

    @GetMapping("/update-supplier")
    public String update(Supplier supplier, RedirectAttributes attributes) {
        try {
            Supplier supp = supplierService.findByName(supplier.getName());
            if (supp != null) {
                if (!supp.getId().equals(supplier.getId())) {
                    attributes.addFlashAttribute("error", "Tên Đã Tồn Tại. Vui lòng nhập Tên khác!");
                    return "redirect:/search-supplier/0?keyword=";
                } else {
                    Supplier suppCoupon = supplierService.update(supplier);
                }
            } else {
                Supplier suppCoupon = supplierService.update(supplier);
            }

            attributes.addFlashAttribute("success", "Updated successfully");
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to update because duplicate name");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Error server");
        }
        return "redirect:/search-supplier/0?keyword=";
    }

    @RequestMapping(value = "/delete-supplier", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deletedProduct(Integer id, RedirectAttributes redirectAttributes, Principal principal) {
        try {
            supplierService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Deleted failed!");
        }
        return "redirect:/search-supplier/0?keyword=";
    }

    @GetMapping("/search-supplier/{pageNo}")
    public String searchSupplier(@PathVariable("pageNo") int pageNo,
                              @RequestParam("keyword") String keyword,
                              Model model, Principal principal) {
        Page<Supplier> listSupplier = supplierService.searchSuppliers(pageNo, keyword);
        model.addAttribute("size", listSupplier.getSize());
        model.addAttribute("listSupplier", listSupplier);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listSupplier.getTotalPages());
        model.addAttribute("supplierNew", new Supplier());
        return "supplier";
    }
}

