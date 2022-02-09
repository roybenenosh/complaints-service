package com.intuit.complaints.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.complaints.dal.Complaint;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class ComplaintSerializer implements Serializer<Complaint> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String s, Complaint o) {
        return new byte[0];
    }

    @Override
    public byte[] serialize(String topic, Headers headers, Complaint data) {
        try {
            if (data == null) return null;
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing Complaint to byte[]");
         }
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}