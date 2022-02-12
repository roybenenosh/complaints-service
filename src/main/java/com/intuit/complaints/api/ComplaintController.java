package com.intuit.complaints.api;

import com.intuit.complaints.contracts.ComplaintContract;
import com.intuit.complaints.contracts.CreateComplaintRequest;
import com.intuit.complaints.core.ComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/complaints")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;

    @PostMapping("/")
    public ResponseEntity<Object> createComplaint(@RequestBody CreateComplaintRequest request) {
        UUID complaintId;
        try {
            complaintId = complaintService.createComplaint(request);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok("Complaint was sent. Complaint ID: " + complaintId.toString());
    }

    @GetMapping("/{complaintId}")
    public ResponseEntity<ComplaintContract> getPurchase(@PathVariable UUID complaintId) {
        ComplaintContract complaintContract;

        try {
            complaintContract = complaintService.getComplaint(complaintId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (complaintContract == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(complaintContract);
    }

    @DeleteMapping("{complaintId}")
    public ResponseEntity<Object> deleteComplaint(@PathVariable UUID complaintId) {
        UUID existing = null;

        try {
            existing = complaintService.deleteComplaint(complaintId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (existing == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok("Complaint was deleted.");
    }

}
