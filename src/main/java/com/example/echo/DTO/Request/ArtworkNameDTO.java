package com.example.echo.DTO.Request;

import com.example.echo.DTO.Response.ResponseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtworkNameDTO extends ResponseDto{
    
    private String name; // 클라이언트로부터 받는 작품 이름
    
}

