package com.intuit.complaints.contracts;

import com.intuit.complaints.core.PurchaseService;
import com.intuit.complaints.core.UserService;
import com.intuit.complaints.dal.Complaint;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ComplaintConverter implements Converter<Complaint, ComplaintContract> {

    private final UserService userService;
    private final PurchaseService purchaseService;

    @Lazy
    public ComplaintConverter(UserService userService, PurchaseService purchaseService) {
        this.userService = userService;
        this.purchaseService = purchaseService;
    }

    @Override
    public ComplaintContract convert(Complaint source) {
        return new ComplaintContract(source.getId(),
                userService.getUser(source.getUserId()),
                source.getSubject(),
                source.getComplaint(),
                purchaseService.getPurchase(source.getPurchaseId()),
                source.getCreationDate());
    }

}
