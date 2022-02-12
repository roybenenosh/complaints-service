package com.intuit.complaints.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.complaints.ComplaintApplication;
import com.intuit.complaints.contracts.ComplaintContract;
import com.intuit.complaints.contracts.CreateComplaintRequest;
import com.intuit.complaints.dal.Complaint;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ComplaintApplication.class)
@AutoConfigureMockMvc
@Slf4j
public class ComplaintsIntegrationTest {

    @Autowired
    private MockMvc mvc;

    private String authorization = "Basic QWxpY2U6MTIzYour";
    private ObjectMapper objectMapper = new ObjectMapper();

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
    public void complaintsFullIntegrationTest() throws Exception {
        // create
        String complaintId = createComplaint();

        Thread.sleep(2000);

        // get the complaint and verify it was created
        ComplaintContract contract = getComplaint(complaintId);
        assertNotNull(contract);
        assertEquals(complaint.getUserId(), contract.getUser().getId());
        assertEquals(complaint.getPurchaseId(), contract.getPurchase().getId());
        assertEquals(complaint.getSubject(), contract.getSubject());
        assertEquals(complaint.getComplaint(), contract.getComplaint());

        // delete
        deleteComplaint(complaintId);

        // verify deletion
        ComplaintContract deleted = getComplaint(complaintId);
        assertNull(deleted);
    }

    private String createComplaint() throws Exception {
        CreateComplaintRequest request = new CreateComplaintRequest(complaint.getUserId(),
                complaint.getSubject(),
                complaint.getComplaint(),
                complaint.getPurchaseId()
        );

        ResultActions createResult = mvc.perform(post("/complaints/")
                        .content(new ObjectMapper().writeValueAsString(request))
                        .header("Authorization", authorization)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String message = createResult.andReturn().getResponse().getContentAsString();
        return message.substring(message.lastIndexOf(" ") + 1, message.length());
    }

    private ComplaintContract getComplaint(String complaintId) throws Exception {
        ResultActions getResult = mvc.perform(get("/complaints/" + complaintId)
                .contentType(MediaType.APPLICATION_JSON));

        String json = getResult.andReturn().getResponse().getContentAsString();
        return json.length() == 0 ? null : objectMapper.readValue(json, ComplaintContract.class);
    }

    private void deleteComplaint(String complaintId) throws Exception {
        mvc.perform(delete("/complaints/" + complaintId)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());;
    }

}
