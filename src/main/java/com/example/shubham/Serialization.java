package com.example.shubham;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class Serialization implements Serializer<User> {
    @Override
    public byte[] serialize(String s,User user) {
        byte[] userToByte = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            userToByte = objectMapper.writeValueAsString(user).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userToByte;
    }
}