package com.mtattab.kafka.controller;

import com.mtattab.kafka.model.MessageModel;
import com.mtattab.kafka.service.FileProcessingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
public class FilesController {


    @Autowired
    private FileProcessingService fileProcessingService;

    @PostMapping(value = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageModel> uploadFile(
            @RequestParam("file") MultipartFile file) {
        MessageModel messageModel = new MessageModel();
        fileProcessingService.storeFile(file);
        messageModel.setMessage("Files uploaded successfully: " + String.join(", ", file.getOriginalFilename()));

        return ResponseEntity.ok(messageModel);
    }

}
