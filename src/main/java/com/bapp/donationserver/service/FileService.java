package com.bapp.donationserver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
public class FileService {

    private final Path dirLocation;
    private final String resourceRootPath = "src/main/resources/file/";

    @Autowired
    public FileService() {
        this.dirLocation = Paths.get(resourceRootPath).toAbsolutePath().normalize();
    }

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(this.dirLocation);
    }

    public String saveFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        Path location = this.dirLocation.resolve(fileName);

        Files.copy(file.getInputStream(), location, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    public Resource loadFile(String fileName) throws FileNotFoundException, MalformedURLException {
        Path file = this.dirLocation.resolve(fileName).normalize();
        Resource resource = new UrlResource(file.toUri());

        log.info("load file path : {}", file.toAbsolutePath());
        if(resource.exists() || resource.isReadable()){
            return resource;
        } else {
            throw new FileNotFoundException("파일을 찾을 수 없습니다.");
        }
    }
}
