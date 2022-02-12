package com.intuit.complaints.core;

import com.intuit.complaints.dal.Complaint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Date;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ComplaintKafkaProducerImplTest {

    @InjectMocks
    private ComplaintKafkaProducerImpl complaintKafkaProducer;
    @Mock
    private KafkaTemplate<String, Complaint> kafkaTemplate;
    @Mock
    private SendResult<String, Complaint> sendResult;
    @Mock
    private ListenableFuture<SendResult<String, Complaint>> responseFuture;

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

        when(kafkaTemplate.send(any(), any())).thenReturn(responseFuture);
        doAnswer(invocationOnMock -> {
            ListenableFutureCallback listenableFutureCallback = invocationOnMock.getArgument(0);
            listenableFutureCallback.onSuccess(sendResult);
            return null;
        }).when(responseFuture).addCallback(any(ListenableFutureCallback.class));

        // when
        complaintKafkaProducer.sendComplaint(complaint);

        // then
        verify(kafkaTemplate).send("complaints", complaint);
    }

}