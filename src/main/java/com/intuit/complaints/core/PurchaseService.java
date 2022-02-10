package com.intuit.complaints.core;

import com.intuit.complaints.dal.Purchase;

import java.util.UUID;

public interface PurchaseService {

    Purchase getPurchase(UUID purchaseId);

}
