package com.example.echo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.echo.DTO.ArtworkDTO;
import com.example.echo.DTO.ArtworkNameDTO;
import com.example.echo.service.ArtworkService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
public class ArtworkController {

    @Autowired
    private ArtworkService artworkService;

    @PostMapping("/get/audio")
    public ResponseEntity<ByteArrayResource> getArtworkVoiceByName(@RequestBody ArtworkNameDTO artworkNameDTO) {
        ByteArrayResource voiceData = artworkService.getArtworkVoiceDataByName(artworkNameDTO.getName());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("audio/mp3"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + artworkNameDTO.getName() + ".mp3\"")
                .body(voiceData);
    }

    @PostMapping("/save/artworks")
    public ResponseEntity<String> saveArtworkWithVoice(@RequestBody ArtworkDTO artworkDTO) {
        try {
            artworkService.saveArtworkWithVoice(artworkDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Artwork saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save artwork: " + e.getMessage());
        }
    }
}
