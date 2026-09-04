package com.example.chatbot.exception;

/**
 * Thrown when the underlying chat model (e.g. Ollama) cannot be reached or
 * fails to produce a response.
 */
public class ChatServiceException extends RuntimeException {

    public ChatServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
