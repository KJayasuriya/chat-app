package com.example.backend.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.backend.model.ChatModel;
import com.example.backend.repository.ChatRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public ChatModel sendMessage(ChatModel message) {
        return chatRepository.save(message);
    }

    public List<ChatModel> getMessages(String sender, String receiver) {
        return chatRepository.findBySenderAndReceiverOrSenderAndReceiver(
                sender, receiver,
                receiver, sender);
    }
}