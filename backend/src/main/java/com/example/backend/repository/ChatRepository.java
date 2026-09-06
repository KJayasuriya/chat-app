package com.example.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository<ChatRepository, String> {
}
