package com.example.echo.DTO.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.echo.common.ResponseCode;
import com.example.echo.common.ResponseMessage;

public class NameCheckResponseDto extends ResponseDto{

    public NameCheckResponseDto() {
        super();
    }
    
    public static ResponseEntity<ResponseDto> existName() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.EXISTENCE_NAME, ResponseMessage.EXISTENCE_NAME);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }

}
