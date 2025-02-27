package com.mtattab.kafka.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;

@Service
@Slf4j
public class KafkaProducerService {





    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    public boolean sendMessage(String topic, List<String> messages) {
        try {
            for (String message : messages) {

                // Create a ProducerRecord with topic, key, and message
//                ProducerRecord<String, String> record =
//                        new ProducerRecord<>(topic, key, message);
//                kafkaTemplate.send(record);
                kafkaTemplate.send(topic,message);

            }

        }catch (Exception e) {
            log.error("Error sending message to Kafka: {}", e.getMessage());
            e.printStackTrace();
            return false;
        }


        return true;
    }

}
