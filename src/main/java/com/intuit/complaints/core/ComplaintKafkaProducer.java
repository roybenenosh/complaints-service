package com.intuit.complaints.core;

import com.intuit.complaints.dal.Complaint;

public interface ComplaintKafkaProducer {

    void sendComplaint(Complaint complaint);

}
