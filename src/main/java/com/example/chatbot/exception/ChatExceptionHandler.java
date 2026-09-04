package com.example.chatbot.exception;

import java.time.Instant;
import java.util.Map;

import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.ai.retry.TransientAiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

/**
 * Translates chat-related failures into clear, structured JSON error
 * responses instead of leaking stack traces to API clients.
 */
@RestControllerAdvice
public class ChatExceptionHandler {

    @ExceptionHandler({ChatServiceException.class, ResourceAccessException.class,
            RestClientException.class, TransientAiException.class, NonTransientAiException.class})
    public ResponseEntity<Map<String, Object>> handleChatServiceUnavailable(Exception ex) {
        Map<String, Object> body = Map.of(
                "error", "Chat service unavailable",
                "message", "Could not reach the Ollama model provider. "
                        + "Ensure Ollama is running and the configured model is pulled.",
                "timestamp", Instant.now().toString());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(body);
    }

}
