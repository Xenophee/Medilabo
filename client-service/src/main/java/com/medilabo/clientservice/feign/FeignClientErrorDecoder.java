package com.medilabo.clientservice.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class FeignClientErrorDecoder implements ErrorDecoder {

    private static final Logger logger = LoggerFactory.getLogger(FeignClientErrorDecoder.class);

    private final ErrorDecoder defaultDecoder = new Default();
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public Exception decode(String methodKey, Response response) {
        String errorMessage = extractErrorMessage(response);

        return switch (response.status()) {
            case 404 -> new ResponseStatusException(HttpStatus.NOT_FOUND);
            case 409 -> new ResponseStatusException(HttpStatus.CONFLICT, errorMessage);
            default -> new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Erreur de communication avec le service distant.");
        };
    }


    private String extractErrorMessage(Response response) {
        try {
            if (response.body() != null) {
                String body = Util.toString(new InputStreamReader(response.body().asInputStream()));
                Error errorResponse = objectMapper.readValue(body, Error.class);
                return errorResponse.getMessage();
            }
        } catch (IOException e) {
            logger.error("Erreur lors de la lecture du message d'erreur : ", e);
            return "Erreur inattendue.";
        }
        return "Erreur inattendue.";
    }

}

