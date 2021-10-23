package com.guomz.missyou.exception;

import com.guomz.missyou.config.ExceptionCodeConfig;
import com.guomz.missyou.domain.resp.ErrResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import java.net.BindException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private ExceptionCodeConfig codeConfig;

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrResponse> handleUnknownException(Exception e){
        e.printStackTrace();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrResponse errResponse = new ErrResponse(9999, codeConfig.getExceptionMsg(9999));
        return new ResponseEntity<>(errResponse, headers, httpStatus);
    }

    @ExceptionHandler(MyHttpException.class)
    @ResponseBody
    public ResponseEntity<ErrResponse> handleHttpException(MyHttpException e){
        e.printStackTrace();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus = HttpStatus.resolve(e.getHttpCode());
        System.out.println(httpStatus);
        ErrResponse errResponse = new ErrResponse(e.getErrCode(), codeConfig.getExceptionMsg(e.getErrCode()));
        return new ResponseEntity<>(errResponse, headers, httpStatus);
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseEntity<ErrResponse> handleParameterException(BindException e){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrResponse errResponse = new ErrResponse(10001, codeConfig.getExceptionMsg(10001));
        return new ResponseEntity<>(errResponse, headers, httpStatus);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<ErrResponse> handleParameterException(ConstraintViolationException e){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrResponse errResponse = new ErrResponse(10001, e.getMessage());
        return new ResponseEntity<>(errResponse, headers, httpStatus);
    }
}
