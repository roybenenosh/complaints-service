package com.intuit.complaints.contracts;

import com.intuit.complaints.dal.Purchase;
import com.intuit.complaints.dal.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintContract {

    private UUID id;
    private User user;
    private String subject;
    private String complaint;
    private Purchase purchase;
    private ZonedDateTime creationDate;

}
