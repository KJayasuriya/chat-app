package com.example.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.model.ChatModel;
import com.example.backend.service.ChatService;
import java.util.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ChatModel sendMessage(@RequestBody ChatModel message) {
        return chatService.sendMessage(message);
    }

    @GetMapping("/{sender}/{receiver}")
    public List<ChatModel> getMessages(@PathVariable String sender, @PathVariable String receiver) {
        return chatService.getMessages(sender, receiver);
    }
}
