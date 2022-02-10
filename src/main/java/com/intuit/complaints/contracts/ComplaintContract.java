package com.intuit.complaints.contracts;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.intuit.complaints.dal.Purchase;
import com.intuit.complaints.dal.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd"
    )
    private Date creationDate;

}
