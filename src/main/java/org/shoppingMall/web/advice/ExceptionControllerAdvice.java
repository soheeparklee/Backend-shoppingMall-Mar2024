package org.shoppingMall.web.advice;

import lombok.extern.slf4j.Slf4j;
import org.shoppingMall.service.exceptions.*;
import org.shoppingMall.web.DTO.email.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.NullPointerException;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidValueException.class)
    public String handleInvalidValueException(InvalidValueException ive){
        log.error("Invalid Value Exception: "+ ive.getMessage());
        return ive.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotAcceptException.class)
    public String handleNotAcceptException(NotAcceptException nae){
        log.error("Not Accept Exception: " + nae.getMessage());
        return  nae.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException nfe){
        log.error("Not Accept Exception:" + nfe.getMessage());
        return  nfe.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointerException(NullPointerException npe){
        log.error("Null pointer Exception: "+ npe.getMessage());
        return npe.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotEnoughStockException.class)
    public String handleNotEnoughStockException(NotEnoughStockException nese){
        log.error("Not Enough Stock Exception: "+ nese.getMessage());
        return nese.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SoldOutException.class)
    public String handleSoldOutException(SoldOutException soe){
        log.error("Sold Out Exception: "+ soe.getMessage());
        return soe.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST) //BadRequestException 가져올 떄 직접 만든 것으로 가져오기 주의, java원래 클래스 가져오면 에러
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException bre){
        log.error("Bad Request Exception: "+ bre.getMessage());
        ErrorResponse errorResponse= new ErrorResponse(400, "Bad Request Exception", bre.getDetailMessage(), bre.getRequest());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
