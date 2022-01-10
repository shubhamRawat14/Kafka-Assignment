package com.example.shubham;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileWriter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Consumer {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "com.example.shubham.Deserialization");
        properties.put("group.id", "user-group");

        KafkaConsumer<String, User> kafkaConsumer = new KafkaConsumer<>(properties);
        List topics = new ArrayList();
        topics.add("mytopic2");
        kafkaConsumer.subscribe(topics);
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            while (true) {
                FileWriter fileWriter = new FileWriter("user-json-data.txt", true);

                ConsumerRecords<String, User> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(1));

                for (ConsumerRecord<String, User> consumerRecord : consumerRecords) {
                    System.out.printf(
                            "Topic: %s, Partition: %d, Value: %s%n",
                            consumerRecord.topic(),
                            consumerRecord.partition(),
                            consumerRecord.value().toString());

                    fileWriter.write(consumerRecord.value().toString() + "\n");
                }
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            kafkaConsumer.close();
        }
    }
}
