package com.assignments.ecomerce.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name="ImportBillDetail")
public class ImportBillDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "importId")
    private ImportBill importBill;
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    private Integer quantity;
    private Double unitPrice;

    public ImportBillDetail(){}

    public void setId(Integer id) {
        this.id = id;
    }

    public void setImportBill(ImportBill importBill) {
        this.importBill = importBill;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public ImportBillDetail(Integer id, ImportBill importBill, Product product, Integer quantity, Double unitPrice) {
        this.id = id;
        this.importBill = importBill;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}
