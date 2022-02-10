package com.intuit.complaints.core;

import com.intuit.complaints.contracts.ComplaintContract;
import com.intuit.complaints.contracts.CreateComplaintRequest;
import com.intuit.complaints.dal.Complaint;

import java.util.UUID;

public interface ComplaintService {

    UUID createComplaint(CreateComplaintRequest request);

    ComplaintContract getComplaint(UUID complaintId);

    UUID deleteComplaint(UUID complaintId);

}
