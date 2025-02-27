package com.mtattab.kafka.listeners;


import com.mtattab.kafka.service.FileProcessingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


import java.nio.file.Path;

import static com.mtattab.kafka.util.Constants.FILE_PROCESSING_TOPIC;
import static com.mtattab.kafka.util.Constants.FILE_PROCESSING_GROUP;

@Slf4j
@Component
public class MyKafkaListener {

    @Autowired
    FileProcessingService fileProcessingService;

    @KafkaListener(topics = FILE_PROCESSING_TOPIC, groupId = FILE_PROCESSING_GROUP)
    public void fileProcessingListener(String message) {
        log.info("Received Message: {}" , message);
        try {
            fileProcessingService.processFile(Path.of(message));

        }catch (Exception e){
            e.printStackTrace();
        }



    }
}
