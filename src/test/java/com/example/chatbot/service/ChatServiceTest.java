package com.example.chatbot.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.client.ResourceAccessException;

import com.example.chatbot.exception.ChatServiceException;

class ChatServiceTest {

    private ChatClient.Builder chatClientBuilder;
    private ChatClient chatClient;
    private ChatClient.ChatClientRequestSpec requestSpec;
    private ChatClient.CallResponseSpec callResponseSpec;

    @BeforeEach
    void setUp() {
        chatClientBuilder = mock(ChatClient.Builder.class);
        chatClient = mock(ChatClient.class);
        requestSpec = mock(ChatClient.ChatClientRequestSpec.class);
        callResponseSpec = mock(ChatClient.CallResponseSpec.class);

        when(chatClientBuilder.build()).thenReturn(chatClient);
        when(chatClient.prompt()).thenReturn(requestSpec);
        when(requestSpec.user(anyString())).thenReturn(requestSpec);
        when(requestSpec.call()).thenReturn(callResponseSpec);
    }

    @Test
    void chatReturnsModelReply() {
        when(callResponseSpec.content()).thenReturn("Hello, human!");

        ChatService chatService = new ChatService(chatClientBuilder);

        String reply = chatService.chat("Hello");

        assertThat(reply).isEqualTo("Hello, human!");
    }

    @Test
    void chatWrapsUnderlyingFailureInChatServiceException() {
        when(callResponseSpec.content()).thenThrow(new ResourceAccessException("connection refused"));

        ChatService chatService = new ChatService(chatClientBuilder);

        assertThatThrownBy(() -> chatService.chat("Hello"))
                .isInstanceOf(ChatServiceException.class)
                .hasCauseInstanceOf(ResourceAccessException.class);
    }

}
