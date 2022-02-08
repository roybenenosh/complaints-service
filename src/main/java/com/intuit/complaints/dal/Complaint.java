package com.intuit.complaints.dal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "complaint")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Complaint {

    @Id
    private UUID id;
    private UUID userId;
    private String subject;
    private String complaint;
    private UUID purchaseId;
    private ZonedDateTime creationDate;
}
