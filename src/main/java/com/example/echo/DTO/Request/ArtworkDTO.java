package com.example.echo.DTO.Request;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class ArtworkDTO {

    private String name;        
    private String description; 
    private String languageCode; 

}
