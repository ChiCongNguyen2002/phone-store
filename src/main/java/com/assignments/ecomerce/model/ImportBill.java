package com.assignments.ecomerce.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@Entity
@Table(name="ImportBill")
public class ImportBill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "supplierId")
    private Supplier supplier;

    private Date importDate;
    private Integer status;
    private Double total;

    @OneToMany(mappedBy = "importBill")
    private List<ImportBillDetail> importBillDetails;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setImportDate(Date importDate){
        this.importDate = importDate;
    }

    public void setSupplier(Supplier supplier) { this.supplier = supplier;}

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void setImportBillDetails(List<ImportBillDetail> importBillDetails) {
        this.importBillDetails = importBillDetails;
    }

    public ImportBill(Integer id, Supplier supplier, Date importDate, Integer status, Double total) {
        this.id = id;
        this.supplier = supplier;
        this.importDate = importDate;
        this.status = status;
        this.total = total;
    }

    public ImportBill(){}
}
