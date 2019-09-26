package com.heshaowei.article_popularize.common.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heshaowei.article_popularize.common.exception.ErrorMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class CheckAdvice {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 请求的 JSON 参数在请求体内的参数校验
     *
     * @param e 异常信息
     * @return 返回数据
     * @throws JsonProcessingException jackson 的异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleBindException1(MethodArgumentNotValidException e) throws JsonProcessingException {
        e.getBindingResult().getAllErrors().forEach(System.out::println);
//        StringBuffer error = new StringBuffer();
//        e.getBindingResult().getAllErrors().forEach(objectError -> error.append(objectError.getDefaultMessage()));
//        return ResponseEntity.badRequest().body(error.toString());
        return new ResponseEntity<>(e.getBindingResult().getAllErrors(), HttpStatus.BAD_REQUEST);
    }

    /**
     * 请求的 URL 参数检验
     *
     * @param e 异常信息
     * @return 返回提示信息
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleBindException2(ConstraintViolationException e) {
        e.getConstraintViolations().forEach(System.out::println);
        StringBuffer error = new StringBuffer();
        e.getConstraintViolations().forEach(constraintViolation -> {
            error.append(constraintViolation.getMessage());
        });
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ErrorMessageException.class)
    public ResponseEntity handleCustomMessageException(ErrorMessageException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}