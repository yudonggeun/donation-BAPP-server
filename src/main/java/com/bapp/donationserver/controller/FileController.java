package com.bapp.donationserver.controller;

import com.bapp.donationserver.data.status.Return;
import com.bapp.donationserver.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.MalformedURLException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/download")
    public ResponseEntity<?> downloadFile(@RequestParam String fileName, HttpServletRequest request)
            throws MalformedURLException, FileNotFoundException {

        Resource resource = fileService.loadFile(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping("/upload")
    public Object upload(MultipartFile file) throws IOException {

        if(file.isEmpty()){
            return Return.failStatus("파일을 전송해주세요");
        }

        String fileName = fileService.saveFile(file);

        StringBuilder sb = new StringBuilder();
        sb.append(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString());
        sb.append("/download?fileName=");
        sb.append(fileName);

        log.info("파일 이름 : {}", file.getOriginalFilename());
        log.info(sb.toString());

        return sb.toString();
    }
}
