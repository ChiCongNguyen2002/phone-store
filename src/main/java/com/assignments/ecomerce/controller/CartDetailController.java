package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.*;
import com.assignments.ecomerce.service.CartDetailService;
import com.assignments.ecomerce.service.CouponService;
import com.assignments.ecomerce.service.ProductService;
import com.assignments.ecomerce.service.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")

public class CartDetailController {
    @Autowired
    private CouponService couponService;
    @Autowired
    private CartDetailService cartDetailService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/GetListItems", produces = "application/json")
    public String index(Model model, Principal principal, @RequestParam("userId") int userId) {
        if (principal != null && principal.getName() != null) {
            User user = userService.findByEmail(principal.getName());
            if (user != null && user.getId() == userId) {
                List<CartDetail> list = cartDetailService.findByUserId(userId);
                double quantity = 0;
                JsonArray jsonArray = new JsonArray();
                for (CartDetail cartDetail : list) {
                    Product product = productService.findById(cartDetail.getProductId());
                    if (product != null) {
                        quantity = cartDetail.getQuantity();
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("product", product.toString());
                        jsonObject.addProperty("quantity", quantity);
                        jsonArray.add(jsonObject);
                    }
                }
                Gson gson = new Gson();
                return gson.toJson(jsonArray);
            } else {
                return "error: User not found";
            }
        } else {
            return "error: Unauthorized";
        }
    }

    @PostMapping("/delete")
    public String delete(HttpServletRequest request, Model model, Principal principal) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        if (principal != null && principal.getName() != null) {
            User user = userService.findByEmail(principal.getName());
            if (user != null && user.getId() == userId) {
                int productId = Integer.parseInt(request.getParameter("productId"));
                String message = "Lỗi xóa sản phẩm! Quý khách vui lòng thử lại sau ít phút!";
                if (cartDetailService.deleteCart(user.getId(), productId)) {
                    message = "Quý khách đã xóa thành công mã sản phẩm " + productId;
                }
                return message;
            } else {
                return "error: User not found";
            }
        } else {
            return "error: Unauthorized";
        }
    }

    @PostMapping("/AddToCart")
    public ResponseEntity<CartViewModel> addToCart(HttpServletRequest request, Principal principal) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        var currentCart = new CartViewModel();
        currentCart.setCartItems(new ArrayList<>());
        if (principal != null && principal.getName() != null) {
            User user = userService.findByEmail(principal.getName());
            if (user != null && user.getId() == userId) {
                int productId = Integer.parseInt(request.getParameter("productId"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                Product product = productService.findById(productId);

                if (product != null) {
                    cartDetailService.saveCart(user.getId(), productId, quantity, product.getPrice());
                    CartDetail cartItem = new CartDetail();
                    cartItem.setProductId(productId);
                    cartItem.setUserId(userId);
                    cartItem.setQuantity(quantity);
                    cartItem.setUnitPrice(product.getPrice());
                    currentCart.CartItems.add(cartItem);
                }
                return ResponseEntity.ok(currentCart);
            } else {
                return ResponseEntity.ok(currentCart);
            }
        } else {
            return ResponseEntity.ok(currentCart);
        }
    }

    @PostMapping("/Coupon/ApplyCoupon")
    @ResponseBody
    public String ApplyCoupon(@RequestParam("code") String code, Model model, Principal principal) {
        Coupon coupon = couponService.findByCode(code);
        if (coupon == null) {
            return "-2";
        } else if (coupon.getCount() <= 0) {
            return "-1";
        }
        int sum = 0;
        int multi = 0;
        int quantity = 0;
        String jsonResult = "";
        if (principal != null && principal.getName() != null) {
            User user = userService.findByEmail(principal.getName());
            List<CartDetail> list = cartDetailService.findByUserId(user.getId());
            JsonArray jsonArray = new JsonArray();

            for (CartDetail cartDetail : list) {
                Product product = productService.findById(cartDetail.getProductId());
                if (product != null) {
                    multi += cartDetail.getQuantity() * product.getPrice();
                    quantity = cartDetail.getQuantity();
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("product", product.toString());
                    jsonObject.addProperty("multi", multi);
                    jsonObject.addProperty("quantity", quantity);
                    jsonArray.add(jsonObject);
                }
            }

            int maGiamGia = coupon.getPromotion();
            sum = multi - (multi * maGiamGia / 100);
            int totalAmountAfterReduction = multi - sum;

            Map<String, Integer> result = new HashMap<>();
            result.put("totalAmountAfterReduction", totalAmountAfterReduction);
            result.put("sum", sum);
            result.put("promotion", maGiamGia);
            Gson gson = new Gson();
            jsonResult = gson.toJson(result);
        }
        return jsonResult;
    }

    @PostMapping("/Cart/UpdateCart")
    public String updateCart(@RequestParam("id") Integer id, Integer quantity, Principal principal) {
        JsonArray jsonArray = new JsonArray();
        Gson gson = new Gson();
        if (principal != null && principal.getName() != null) {
            Product product1 = productService.getProductById(id);
            User user = userService.findByEmail(principal.getName());
            List<CartDetail> list = cartDetailService.findByUserId(user.getId());
            String message = "";
            for (CartDetail cartDetail : list) {
                Product product = productService.findById(cartDetail.getProductId());
                if (product != null) {
                    if (product.getId().equals(id)) {
                        if (quantity > product1.getQuantity()) {
                            message = "quantity is greater than stock";
                            return gson.toJson(message);
                        } else if (quantity == 0) {
                            cartDetailService.deleteCart(user.getId(), product.getId());
                        } else {
                            cartDetailService.updateCart(user.getId(), product.getId(), quantity, product.getPrice());
                        }
                    }
                }
            }
        }
        return gson.toJson(jsonArray);
    }
}
