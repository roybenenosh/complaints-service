package com.intuit.complaints.core;

import com.intuit.complaints.contracts.ComplaintContract;
import com.intuit.complaints.contracts.CreateComplaintRequest;
import com.intuit.complaints.dal.Complaint;
import com.intuit.complaints.dal.ComplaintRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final UserService userService;
    private final PurchaseService purchaseService;
    private final ComplaintKafkaProducer complaintKafkaProducer;

    @Override
    public UUID createComplaint(CreateComplaintRequest request) {
        if (requestIsInvalid(request)) {
            return null;
        }

        Complaint complaint = new Complaint(UUID.randomUUID(),
                request.getUserId(),
                request.getSubject(),
                request.getComplaint(),
                request.getPurchaseId(),
                new Date());

        log.info("Sending complaint: " + complaint);
        complaintKafkaProducer.sendComplaint(complaint);
        return complaint.getId();
    }

    @Override
    public ComplaintContract getComplaint(UUID complaintId) {
        log.info("Fetching complaint: " + complaintId.toString());
        Optional<Complaint> dbItem = complaintRepository.findById(complaintId);

        if (dbItem.isEmpty()) return null;

        Complaint complaint = dbItem.get();

        return new ComplaintContract(complaint.getId(),
                userService.getUser(complaint.getUserId()),
                complaint.getSubject(),
                complaint.getComplaint(),
                purchaseService.getPurchase(complaint.getPurchaseId()),
                complaint.getCreationDate()
        );
    }

    @Override
    public UUID deleteComplaint(UUID complaintId) {
        log.info("Deleting complaint: " + complaintId.toString());
        log.info("Deleting complaint: " + complaintId.toString());
        Optional<Complaint> complaint = complaintRepository.findById(complaintId);

        if (complaint.isEmpty()) {
            return null;
        }

        complaintRepository.deleteById(complaintId);
        return complaintId;
    }

    private boolean requestIsInvalid(CreateComplaintRequest request) {
        return request.getUserId() == null ||
                request.getPurchaseId() == null ||
                request.getSubject() == null ||
                request.getComplaint() == null;
    }

}
