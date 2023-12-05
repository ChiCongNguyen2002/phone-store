package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.Category;
import com.assignments.ecomerce.model.Coupon;
import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.service.CategoryService;
import com.assignments.ecomerce.service.CouponService;
import com.assignments.ecomerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class CouponController {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CouponService couponService;

    @GetMapping("/coupon/{pageNo}")
    public String getAllCoupons(@PathVariable("pageNo") int pageNo, Model model) {
        Page<Coupon> listCoupon = couponService.pageCoupon(pageNo);
        model.addAttribute("listCoupon", listCoupon);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listCoupon.getTotalPages());
        model.addAttribute("couponNew", new Coupon());
        return "coupon";
    }

    @GetMapping("/couponCustomer/{pageNo}")
    public String getAllCouponCustomer(@PathVariable("pageNo") int pageNo,Model model,Principal principal) {

        List<Product> listProducts = productService.getAllProducts();
        List<Category> categories = categoryService.getAllCategory();
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Page<Coupon> listCoupon = couponService.pageCoupon(pageNo);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listCoupon.getTotalPages());
        model.addAttribute("listCoupon", listCoupon);
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("categories", categories);
        model.addAttribute("userDetails", userDetails);
        return "couponCustomer";
    }

    @GetMapping("/detailCoupon/{id}")
    public String getDetailCouponCustomer(@PathVariable("id") Integer id,Model model,Principal principal) {
        List<Product> listProducts = productService.getAllProducts();
        List<Category> categories = categoryService.getAllCategory();
        Coupon coupon = couponService.findById(id);
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("coupon", coupon);
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("categories", categories);
        return "detailCoupon";
    }

    @PostMapping("/add-coupon")
    public String add(@ModelAttribute("couponNew") Coupon coupon, Model model, RedirectAttributes attributes) {
        try {
            Coupon coup = couponService.findByCode(coupon.getCode());
            if (coup == null) {
                couponService.save(coupon);
            } else if(coup.getStatus() == 0){
                couponService.updateStatus(coup.getId());
            }else if(coup.getStatus() == 1){
                attributes.addFlashAttribute("error", "Mã Code Đã Tồn Tại Vui lòng nhập Mã khác!");
                return "redirect:/search-coupon/0?keyword=";
            }
            model.addAttribute("couponNew", coupon);
            attributes.addFlashAttribute("success", "Added successfully");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            attributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            attributes.addFlashAttribute("error", "Error Server");
        }
        return "redirect:/search-coupon/0?keyword=";
    }

    @RequestMapping(value = "/findByIdCoupon/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Coupon findById(@PathVariable("id") Integer id){
        return couponService.findById(id);
    }


    @GetMapping("/update-coupon")
    public String update(Coupon coupon, RedirectAttributes attributes) {
        try {
            Coupon existingCoupon = couponService.findByCode(coupon.getCode());
            if (existingCoupon != null) {
                if (!existingCoupon.getId().equals(coupon.getId())) {
                    attributes.addFlashAttribute("error", "Mã Code Đã Tồn Tại. Vui lòng nhập Mã khác!");
                } else {
                    Coupon updatedCoupon = couponService.update(coupon);
                    attributes.addFlashAttribute("success", "Updated successfully");
                }
            } else {
                Coupon updatedCoupon = couponService.update(coupon);
                attributes.addFlashAttribute("success", "Updated successfully");
            }
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to update because duplicate name");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Error server");
        }
        return "redirect:/search-coupon/0?keyword=";
    }

    @RequestMapping(value = "/delete-coupon", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(Integer id, RedirectAttributes attributes) {
        try {
            couponService.deleteById(id);
            attributes.addFlashAttribute("success", "Deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to deleted");
        }
        return "redirect:/search-coupon/0?keyword=";
    }

    @GetMapping("/search-coupon/{pageNo}")
    public String searchCoupon(@PathVariable("pageNo") int pageNo,
                                 @RequestParam("keyword") String keyword,
                                 Model model, Principal principal) {
        Page<Coupon> listCoupon = couponService.searchCoupon(pageNo, keyword);
        model.addAttribute("size", listCoupon.getSize());
        model.addAttribute("listCoupon", listCoupon);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listCoupon.getTotalPages());
        model.addAttribute("couponNew", new Coupon());
        return "coupon";
    }
}
