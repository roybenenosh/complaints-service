package com.intuit.complaints.core;

import com.intuit.complaints.dal.Complaint;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ComplaintKafkaProducerImpl implements ComplaintKafkaProducer {

    private final KafkaTemplate<String, Complaint> kafkaTemplate;

    private final String KAFKA_COMPLAINTS_TOPIC = "complaints";

    @Override
    public void sendComplaint(Complaint complaint) {
        kafkaTemplate.send(KAFKA_COMPLAINTS_TOPIC, complaint);
    }
}
