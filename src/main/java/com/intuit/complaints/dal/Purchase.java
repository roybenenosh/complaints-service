package com.intuit.complaints.dal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {
    private UUID id;
    private UUID userId;
    private UUID productId;
    private String productName;
    private double pricePaidAmount;
    private String priceCurrency;
    private double discountPercent;
    private UUID merchantId;
    private Date purchaseDate;

}
