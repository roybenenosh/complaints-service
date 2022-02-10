package com.intuit.complaints.core;

import com.intuit.complaints.dal.Complaint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Date;
import java.util.UUID;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ComplaintKafkaProducerImplTest {

    @InjectMocks
    private ComplaintKafkaProducerImpl complaintKafkaProducer;
    @Mock
    private KafkaTemplate<String, Complaint> kafkaTemplate;

    @Test
    public void sendComplaintTest() {
        // given
        Complaint complaint = new Complaint(
                UUID.randomUUID(),
                UUID.fromString("a93adc57-4d59-4a9d-85c6-b5d48d99101d"),
                "Product didnt arrive",
                "The product didnt arrive",
                UUID.fromString("f256c996-6dcb-40cf-8dce-a11fa9bcab6b"),
                new Date()
        );

        // when
        complaintKafkaProducer.sendComplaint(complaint);

        // then
        verify(kafkaTemplate).send("complaints", complaint);
    }

}