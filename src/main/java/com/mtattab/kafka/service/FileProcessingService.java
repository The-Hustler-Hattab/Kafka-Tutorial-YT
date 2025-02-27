package com.mtattab.kafka.service;

import com.mtattab.kafka.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class FileProcessingService {

    private final Path fileStorageLocation;

    private final Path fileTargetLocation;
    private final Path fileExceptionLocation;



    @Autowired
    KafkaProducerService kafkaProducerService;


    public FileProcessingService(@Value("${file.upload-dir}") String uploadDir,
                                 @Value("${file.target-dir}") String fileTargetLocation,
                                 @Value("${file.exception-dir}") String fileExceptionLocation) {
        this.fileStorageLocation = Paths.get(uploadDir)
                .toAbsolutePath().normalize();
        this.fileTargetLocation = Paths.get(fileTargetLocation)
                .toAbsolutePath().normalize();
        this.fileExceptionLocation = Paths.get(fileExceptionLocation)
                .toAbsolutePath().normalize();

        try {
            Files.createDirectory(this.fileStorageLocation);
            Files.createDirectory(this.fileTargetLocation);
            Files.createDirectory(this.fileExceptionLocation);

        }catch (FileAlreadyExistsException ignored){

        }
        catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public void storeFile(MultipartFile file) {
        // Get the original filename and sanitize it
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Prevent directory traversal attacks
            if (originalFileName.contains("..")) {
                throw new RuntimeException("Invalid path sequence " + originalFileName);
            }

            // Generate timestamped filename
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String sanitizedFileName = originalFileName.replaceAll("[^a-zA-Z0-9._-]", "_"); // Remove special chars
            String newFileName = timeStamp + "_" + sanitizedFileName; // Prepend timestamp

            // Define the target location
            Path targetLocation = this.fileStorageLocation.resolve(newFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Send the new filename to Kafka
            kafkaProducerService.sendMessage(Constants.FILE_PROCESSING_TOPIC,
                    Collections.singletonList(targetLocation.toString()));

        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + originalFileName + ". Please try again!", ex);
        }
    }


    public void processFile(Path file) throws IOException {
        log.info("[+] Processing file: {}", file);
        try {

            Path successPath = Path.of(String.valueOf(fileTargetLocation),file.getFileName().toString());
            Files.move(file,successPath, StandardCopyOption.REPLACE_EXISTING);
            log.info("[+] file processed successfully");

        }catch (Exception e){
            e.printStackTrace();
            Path failPath = Path.of(String.valueOf(fileExceptionLocation),file.getFileName().toString());

            Files.move(file,failPath, StandardCopyOption.REPLACE_EXISTING);

        }
    }


}
