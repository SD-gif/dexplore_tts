package com.example.echo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.echo.DTO.Request.ArtworkDTO;
import com.example.echo.DTO.Request.ArtworkNameDTO;
import com.example.echo.service.ArtworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;
import com.example.echo.service.S3UploadService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;


@RestController
@RequiredArgsConstructor
public class ArtworkController {

    @Autowired
    private ArtworkService artworkService;

    @Autowired
    private S3UploadService s3UploadService;

    @PostMapping("/saveTTS")
    public ResponseEntity<String> saveArtworkWithVoice(@RequestBody ArtworkDTO artworkDTO) {
        try {
            artworkService.saveArtworkWithVoice(artworkDTO);
            String imageUrl = s3UploadService.saveFile(artworkService.byteArrayToMultipartFile(artworkDTO.getName()));

            return ResponseEntity.status(HttpStatus.CREATED).body("TTS uploaded successfully. URL: " + imageUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save artwork: " + e.getMessage());
        }
    }

    @PostMapping("/getTTS")
    public void getS3ObjectUrl(@RequestBody ArtworkNameDTO artworkNameDTO, HttpServletResponse response) throws IOException {
        URL url = s3UploadService.generatePresignedUrl(artworkNameDTO.getName());
        response.sendRedirect(url.toString());
    }
}
