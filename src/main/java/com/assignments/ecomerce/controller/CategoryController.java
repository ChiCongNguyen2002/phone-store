package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.Category;
import com.assignments.ecomerce.model.Supplier;
import com.assignments.ecomerce.service.CategoryService;
import com.assignments.ecomerce.service.SupplierService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/category/{pageNo}")
    public String getAllCategory(@PathVariable("pageNo") int pageNo, Model model, Principal principal) {

        Page<Category> listCategory = categoryService.pageCategory(pageNo);
        List<Supplier> listSuppliers =  supplierService.getAllSuppliers();
        model.addAttribute("listSuppliers", listSuppliers);
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listCategory.getTotalPages());
        model.addAttribute("categoryNew", new Category());
        return "category";
    }

    @PostMapping("/add-category")
    public String add(@ModelAttribute("categoryNew") Category category,
                      Model model,
                      RedirectAttributes attributes) {
        try {
            Category categ = categoryService.findByName(category.getName());
            if (categ == null) {
                categoryService.save(category);
            } else if(categ.getStatus() == 0){
                categoryService.updateStatus(categ.getId());
            }else if(categ.getStatus() == 1){
                attributes.addFlashAttribute("failed", "Tên Danh Mục Đã Tồn Tại Vui lòng nhập tên khác!");
                return "redirect:/search-category/0?keyword=";
            }

            model.addAttribute("categoryNew", category);
            attributes.addFlashAttribute("success", "Added successfully");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            attributes.addFlashAttribute("failed", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            attributes.addFlashAttribute("failed", "Error Server");
        }
        return "redirect:/search-category/0?keyword=";
    }

    @RequestMapping(value = "/findByIdCategory/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Category findById(@PathVariable("id") Integer id) {
        return categoryService.findById(id);
    }

    @GetMapping("/update-category")
    public String update(Category category, RedirectAttributes attributes, Model model) {
        try {
            categoryService.update(category);
            attributes.addFlashAttribute("success", "Updated successfully");
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to update because duplicate name");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Error server");
        }
        return "redirect:/search-category/0?keyword=";
    }

    @RequestMapping(value = "/status-category", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enabledProduct(Integer id, RedirectAttributes redirectAttributes, Principal principal) {
        try {
            categoryService.enableById(id);
            redirectAttributes.addFlashAttribute("success", "Delete successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Enabled failed!");
        }
        return "redirect:/search-category/0?keyword=";
    }

    @GetMapping("/search-category/{pageNo}")
    public String searchCategory(@PathVariable("pageNo") int pageNo,
                                 @RequestParam("keyword") String keyword,
                                 Model model, Principal principal, HttpSession session) {
        Page<Category> listCategory = categoryService.searchCategory(pageNo, keyword);
        List<Supplier> listSuppliers =  supplierService.getAllSuppliers();
        model.addAttribute("listSuppliers", listSuppliers);
        session.setAttribute("keyword", keyword);
        model.addAttribute("keyword", keyword);
        model.addAttribute("size", listCategory.getSize());
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listCategory.getTotalPages());
        model.addAttribute("categoryNew", new Category());
        return "category";
    }

}
