package com.intuit.complaints.core;

import com.intuit.complaints.contracts.ComplaintContract;
import com.intuit.complaints.dal.Complaint;

import java.util.UUID;

public interface ComplaintService {

    ComplaintContract getComplaint(UUID id);

}
