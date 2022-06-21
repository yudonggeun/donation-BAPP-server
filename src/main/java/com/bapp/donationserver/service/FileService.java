package com.bapp.donationserver.service;

import com.bapp.donationserver.exception.IllegalUserDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.UUID;

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

        //확장자 추출
        int index = fileName.lastIndexOf('.');
        if(index == -1){
            throw new IllegalUserDataException("파일에 확장자가 없습니다.");
        }
        String type = fileName.substring(index);

        //랜덤한 파일 이름 생성
        fileName = StringUtils.concat("(", LocalDate.now(), ")", UUID.randomUUID(), type);

        //파일 저장
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
