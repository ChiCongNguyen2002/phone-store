package com.assignments.ecomerce.service.impl;

import com.assignments.ecomerce.model.CartDetail;
import com.assignments.ecomerce.repository.CartDetailRepository;
import com.assignments.ecomerce.service.CartDetailService;
import com.assignments.ecomerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class CartDetailServiceImpl implements CartDetailService {
    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Autowired
    private ProductService productService;

    public List<CartDetail> findAll() {
        return cartDetailRepository.findAll();
    }
    public List<CartDetail> findByUserId(Integer userId) {
        return cartDetailRepository.findByUserId(userId);
    }

    public CartDetail findByUserIdAndProductId(Integer userId, Integer productId) {
        return cartDetailRepository.findByUserIdAndProductId(userId, productId);
    }

    @Transactional
    public boolean saveCart(Integer userId, Integer productId, Integer quantity, Integer unitPrice) {
        try {
            // Kiểm tra sự tồn tại của bản ghi với userId và productId
            CartDetail existingCartDetail = cartDetailRepository.findByUserIdAndProductId(userId, productId);
            if (existingCartDetail != null) {
                // Nếu bản ghi đã tồn tại, tăng quantity lên 1
                existingCartDetail.setQuantity(existingCartDetail.getQuantity() + quantity);
                cartDetailRepository.save(existingCartDetail);
            } else {
                // Nếu bản ghi không tồn tại, tạo mới CartDetail và lưu vào cơ sở dữ liệu
                int oldQuantity = 0;
                CartDetail cartDetail = new CartDetail();
                cartDetail.setUserId(userId);
                cartDetail.setProductId(productId);
                cartDetail.setQuantity(quantity);
                cartDetail.setUnitPrice(unitPrice);
                cartDetailRepository.saveCartDetail(cartDetail.getUserId(), cartDetail.getProductId(), cartDetail.getQuantity(), cartDetail.getUnitPrice());
                CartDetail updatedCartDetail = cartDetailRepository.findByUserIdAndProductId(userId, productId);
                return updatedCartDetail.getQuantity() - oldQuantity == quantity;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateCart(Integer userId, Integer productId, Integer quantity, Integer unitPrice) {
        try {
            // Kiểm tra sự tồn tại của bản ghi với userId và productId
            CartDetail existingCartDetail = cartDetailRepository.findByUserIdAndProductId(userId, productId);
            if (existingCartDetail != null) {
                existingCartDetail.setQuantity(quantity);
                cartDetailRepository.save(existingCartDetail);
            } else {
                // Nếu bản ghi không tồn tại, tạo mới CartDetail và lưu vào cơ sở dữ liệu
                int oldQuantity = 0;
                CartDetail cartDetail = new CartDetail();
                cartDetail.setUserId(userId);
                cartDetail.setProductId(productId);
                cartDetail.setQuantity(quantity);
                cartDetail.setUnitPrice(unitPrice);
                cartDetailRepository.saveCartDetail(cartDetail.getUserId(), cartDetail.getProductId(), cartDetail.getQuantity(), cartDetail.getUnitPrice());
                CartDetail updatedCartDetail = cartDetailRepository.findByUserIdAndProductId(userId, productId);
                if (updatedCartDetail.getQuantity() - oldQuantity == quantity) {
                    return true;
                } else {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("Lỗi ở đây " + e.toString());
            return false;
        }
    }

    @Transactional
    public boolean deleteCart(Integer userId, Integer productId) {
        try {
            // Kiểm tra sự tồn tại của bản ghi với userId và productId
            CartDetail existingCartDetail = cartDetailRepository.findByUserIdAndProductId(userId, productId);
            if (existingCartDetail != null) {
                cartDetailRepository.delete(existingCartDetail);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
