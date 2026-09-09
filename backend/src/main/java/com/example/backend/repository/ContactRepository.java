package com.example.backend.repository;

import java.util.List;
import com.example.backend.model.ContactModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContactRepository extends MongoRepository<ContactModel, String> {
    List<ContactModel> findByOwner(String owner);

    ContactModel findByOwnerAndUsername(String owner, String username);
}