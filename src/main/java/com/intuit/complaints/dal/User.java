package com.intuit.complaints.dal;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private UUID id;
    private String fullName;
    private String emailAddress;
    private String physicalAddress;
}
