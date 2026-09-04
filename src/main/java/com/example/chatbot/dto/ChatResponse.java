package com.example.chatbot.dto;

/**
 * Response payload returned from the chat endpoint.
 *
 * @param reply the chat model's reply
 */
public record ChatResponse(String reply) {
}
