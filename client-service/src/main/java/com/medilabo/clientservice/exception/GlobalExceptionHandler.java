package com.medilabo.clientservice.exception;


import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(ResponseStatusException.class)
    public String handleResponseStatusException(ResponseStatusException ex, HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());

        if (status == HttpStatus.NOT_FOUND) return "views/404";

        redirectAttributes.addFlashAttribute("errorMessage", ex.getReason());
        String currentView = request.getRequestURI();
        return "redirect:" + currentView;
    }


    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String httpMethod = request.getMethod();
        logger.error("Erreur sur {} avec la méthode {} : ", requestUri, httpMethod, ex);
        return "views/error";
    }
}
