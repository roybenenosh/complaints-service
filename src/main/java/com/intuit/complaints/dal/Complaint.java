package com.intuit.complaints.dal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "complaints")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Complaint {

    @Id
    private UUID id;
    @Column(name = "user_id")
    private UUID userId;
    @Column(name = "subject")
    private String subject;
    @Column(name = "complaint")
    private String complaint;
    @Column(name = "purchase_id")
    private UUID purchaseId;
    @Column(name = "creation_date")
    private ZonedDateTime creationDate;
}
