package com.example.echo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import com.google.cloud.texttospeech.v1.*;
import com.example.echo.DTO.ArtworkDTO;
import com.example.echo.Data.Artwork;
import com.example.echo.Repository.ArtworkRepository;
import com.google.protobuf.ByteString;

@Service
public class ArtworkService {
    
    @Autowired
    private ArtworkRepository artworkRepository;
    
    public void saveArtworkWithVoice(ArtworkDTO artworkDTO) throws Exception {
        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
            SynthesisInput input = SynthesisInput.newBuilder().setText(artworkDTO.getDescription()).build();
            VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
                    .setLanguageCode(artworkDTO.getLanguageCode())
                    .setSsmlGender(SsmlVoiceGender.NEUTRAL)
                    .build();
            AudioConfig audioConfig = AudioConfig.newBuilder()
                    .setAudioEncoding(AudioEncoding.MP3)
                    .build();
            
            SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);
            ByteString audioContents = response.getAudioContent();
            
            Artwork artwork = new Artwork();
            artwork.setName(artworkDTO.getName());
            artwork.setDescription(artworkDTO.getDescription());
            artwork.setLanguageCode(artworkDTO.getLanguageCode());
            artwork.setVoiceData(audioContents.toByteArray());
            artworkRepository.save(artwork);
        }
    }

    public ByteArrayResource getArtworkVoiceDataByName(String name) {
        Artwork artwork = artworkRepository.findByName(name)
        .orElseThrow(() -> new ResourceNotFoundException("Artwork not found with name " + name));

        return new ByteArrayResource(artwork.getVoiceData());
    }

    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
    
}

