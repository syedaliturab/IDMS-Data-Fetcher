package com.drivesoft.idmsdatafetcher.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "account")
@Data
public class Account {
    @Id
    @Column(name = "id" ,nullable = false)
    private String acctID;
    @Column(name = "account_type")
    private String acctType;
    @Column(name = "sales_group_person1_id")
    private String salesGroupPerson1ID;
    @Column(name = "contract_date")
    private String contractDate;
    @Column(name = "collateral_stock_number")
    private String collateralStockNumber;
    @Column(name= "collateral_year_model")
    private String collateralYearModel;
    @Column(name= "collateral_make")
    private String collateralMake;
    @Column(name= "collateral_model")
    private String collateralModel;
    @Column(name="borrower1_first_name")
    private String borrower1FirstName;
    @Column(name="borrower1_last_name")
    private String borrower1LastName;
    @Column(name="contract_sales_price")
    private  String contractSalesPrice;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdA6;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
