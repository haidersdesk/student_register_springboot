package com.example.students.controller;

import com.example.students.exception.BadRequestException;
import com.example.students.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public Model handleNotFoundException(NotFoundException ex, Model model) {
        return model.addAttribute("msg",ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public Model handleBadRequestException(BadRequestException ex, Model model) {
         return model.addAttribute("msg",ex.getMessage());
    }

    //For All Other Exceptions
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason="This Action is Forbidden")
    public Model handleExceptions(Exception ex, Model model){
        return model.addAttribute("msg",ex.getMessage());

    }


}

