package com.intuit.complaints.core;

import com.intuit.complaints.dal.Complaint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@RequiredArgsConstructor
@Slf4j
public class ComplaintKafkaProducerImpl implements ComplaintKafkaProducer {

    private final KafkaTemplate<String, Complaint> kafkaTemplate;

    private final String KAFKA_COMPLAINTS_TOPIC = "complaints";

    @Override
    public void sendComplaint(Complaint complaint) {
        ListenableFuture<SendResult<String, Complaint>> future = kafkaTemplate.send(KAFKA_COMPLAINTS_TOPIC, complaint);

        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, Complaint> result) {
                log.info("Complaint was sent successfully");
            }

            @Override
            public void onFailure(Throwable e) {
                // add to a scheduled process which fetch the complaints and re-sends them
            }
        });
    }
}
