package com.intuit.complaints.core;

import com.intuit.complaints.contracts.ComplaintContract;
import com.intuit.complaints.contracts.CreateComplaintRequest;
import com.intuit.complaints.dal.Complaint;
import com.intuit.complaints.dal.ComplaintRepository;
import com.intuit.complaints.dal.Purchase;
import com.intuit.complaints.dal.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ComplaintServiceImplTest {

    @InjectMocks
    private ComplaintServiceImpl complaintService;
    @Mock
    private ComplaintRepository complaintRepository;
    @Mock
    private UserService userService;
    @Mock
    private PurchaseService purchaseService;
    @Mock
    private ComplaintKafkaProducer complaintKafkaProducer;

    private Complaint complaint;

    @Before
    public void init() {
        complaint = new Complaint(
                UUID.randomUUID(),
                UUID.fromString("a93adc57-4d59-4a9d-85c6-b5d48d99101d"),
                "Product didnt arrive",
                "The product didnt arrive",
                UUID.fromString("f256c996-6dcb-40cf-8dce-a11fa9bcab6b"),
                new Date()
        );
    }

    @Test
    public void createComplaintTest() {
        // given
        CreateComplaintRequest request = new CreateComplaintRequest(complaint.getUserId(),
                complaint.getSubject(),
                complaint.getComplaint(),
                complaint.getPurchaseId()
        );

        // when
        UUID complaintId = complaintService.createComplaint(request);

        // then
        verify(complaintKafkaProducer).sendComplaint(any(Complaint.class));
        assertNotNull(complaintId);
    }

    @Test
    public void createComplaintInvalidRequestTest() {
        // given
        CreateComplaintRequest request = new CreateComplaintRequest(complaint.getUserId(),
                complaint.getSubject(),
                complaint.getComplaint(),
                null
        );

        // when
        UUID complaintId = complaintService.createComplaint(request);

        // then
        assertNull(complaintId);
    }

    @Test
    public void getComplaintTest() {
        // given
        User user = new User(UUID.fromString("a93adc57-4d59-4a9d-85c6-b5d48d99101d"),
                "John Doe", "johndoe@test.com",
                "Test Lane 1 Los Angeles"
        );

        Purchase purchase = new Purchase(UUID.fromString("f256c996-6dcb-40cf-8dce-a11fa9bcab6b"),
                UUID.fromString("a93adc57-4d59-4a9d-85c6-b5d48d99101d"),
                UUID.fromString("4ac9da0b-66eb-415c-9153-a59ec0b3c3fe"),
                "Book",
                13.2,
                "USD",
                0.1,
                UUID.fromString("71e234d2-dc65-41ff-bada-9d08d82fc6d1"),
                new Date()
        );

        user.setId(complaint.getUserId());
        UUID complaintId = complaint.getId();
        when(complaintRepository.findById(complaintId)).thenReturn(Optional.of(complaint));
        when(userService.getUser(complaint.getUserId())).thenReturn(user);
        when(purchaseService.getPurchase(complaint.getPurchaseId())).thenReturn(purchase);

        // when
        ComplaintContract contract = complaintService.getComplaint(complaintId);

        // then
        assertEquals(contract.getComplaint(), complaint.getComplaint());
        assertEquals(contract.getSubject(), complaint.getSubject());
        assertEquals(contract.getCreationDate(), complaint.getCreationDate());
        assertEquals(contract.getUser().getId(), complaint.getUserId());
    }

    @Test(expected = Exception.class)
    public void getComplaintErrorTest() {
        // given
        UUID complaintId = complaint.getId();
        when(complaintRepository.findById(complaintId)).thenThrow(new Exception());

        // when
        complaintService.getComplaint(complaintId);
    }

    @Test
    public void deleteComplaintTest() {
        // given
        UUID complaintId = complaint.getId();
        when(complaintRepository.findById(complaintId)).thenReturn(Optional.of(complaint));

        // when
        complaintService.deleteComplaint(complaintId);

        // then
        verify(complaintRepository).deleteById(complaintId);
    }

    @Test(expected = Exception.class)
    public void deleteComplaintErrorTest() {
        // given
        UUID complaintId = complaint.getId();
        doThrow(new Exception()).when(complaintRepository).deleteById(complaintId);

        // when
        complaintService.getComplaint(complaintId);
    }

}