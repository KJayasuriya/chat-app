package com.example.backend.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.backend.model.ChatModel;

public interface ChatRepository extends MongoRepository<ChatModel, String> {

    List<ChatModel> findBySenderAndReceiverOrSenderAndReceiver(
            String sender1,
            String receiver1,
            String sender2,
            String receiver2);
}