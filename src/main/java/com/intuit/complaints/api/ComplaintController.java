package com.intuit.complaints.api;

import com.intuit.complaints.contracts.ComplaintContract;
import com.intuit.complaints.core.ComplaintService;
import com.intuit.complaints.dal.Purchase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/purchases")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;

    @GetMapping("/{complaintId}")
    public ResponseEntity<ComplaintContract> getPurchase(@PathVariable UUID complaintId) {
        ComplaintContract complaintContract = complaintService.getComplaint(complaintId);

        if (complaintContract == null) {
            ResponseEntity.notFound();
        }

        return ResponseEntity.ok(complaintContract);
    }

}
