package com.intuit.complaints.contracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateComplaintRequest {

    private UUID userId;
    private String subject;
    private String complaint;
    private UUID purchaseId;


}
