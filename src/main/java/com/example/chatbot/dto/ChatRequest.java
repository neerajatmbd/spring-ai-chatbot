package com.example.chatbot.dto;

/**
 * Request payload for the chat endpoint.
 *
 * @param message the user's message to send to the chat model
 */
public record ChatRequest(String message) {
}
