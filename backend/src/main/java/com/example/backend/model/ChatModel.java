package com.example.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chat")
public class ChatModel {
    @Id
    private String id;
    private String sender;
    private String receiver;
    private String message;
}
