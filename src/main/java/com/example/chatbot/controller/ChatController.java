package com.example.chatbot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.chatbot.dto.ChatRequest;
import com.example.chatbot.dto.ChatResponse;
import com.example.chatbot.service.ChatService;

/**
 * REST controller exposing the chatbot API.
 */
@RestController
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/api/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        String reply = chatService.chat(requireNonBlank(request == null ? null : request.message()));
        return ResponseEntity.ok(new ChatResponse(reply));
    }

    @GetMapping("/api/chat")
    public ResponseEntity<ChatResponse> chat(@RequestParam("message") String message) {
        String reply = chatService.chat(requireNonBlank(message));
        return ResponseEntity.ok(new ChatResponse(reply));
    }

    private String requireNonBlank(String message) {
        if (message == null || message.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "'message' must not be blank");
        }
        return message;
    }

}
