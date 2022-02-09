package com.intuit.complaints.core;

import com.intuit.complaints.contracts.ComplaintContract;
import com.intuit.complaints.contracts.CreateComplaintRequest;
import com.intuit.complaints.dal.Complaint;
import com.intuit.complaints.dal.ComplaintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final UserService userService;
    private final PurchaseService purchaseService;
    private final ComplaintKafkaProducer complaintKafkaProducer;

    @Override
    public ComplaintContract getComplaint(UUID id) {
        Optional<Complaint> dbItem = complaintRepository.findById(id);

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
    public void createComplaint(CreateComplaintRequest request) {
        Complaint complaint = new Complaint(UUID.randomUUID(),
                request.getUserId(),
                request.getSubject(),
                request.getComplaint(),
                request.getPurchaseId(),
                ZonedDateTime.now());

        complaintKafkaProducer.sendComplaint(complaint);
    }

}
