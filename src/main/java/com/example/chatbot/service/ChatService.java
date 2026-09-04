package com.example.chatbot.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.ai.retry.TransientAiException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.example.chatbot.exception.ChatServiceException;

/**
 * Thin wrapper around Spring AI's {@link ChatClient} that keeps the
 * controller free of any direct interaction with the chat model.
 */
@Service
public class ChatService {

    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    /**
     * Sends the given message to the configured chat model and returns its
     * reply.
     *
     * @param message the user's message
     * @return the model's reply
     * @throws ChatServiceException if the chat model cannot be reached or
     *                               fails to produce a response
     */
    public String chat(String message) {
        try {
            return chatClient.prompt()
                    .user(message)
                    .call()
                    .content();
        } catch (RestClientException | TransientAiException | NonTransientAiException ex) {
            throw new ChatServiceException("Failed to get a response from the chat model", ex);
        }
    }

}
