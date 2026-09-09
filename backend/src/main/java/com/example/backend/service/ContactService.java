package com.example.backend.service;

import org.springframework.stereotype.Service;
import java.util.*;
import com.example.backend.model.ContactModel;
import com.example.backend.repository.ContactRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;

    public ContactModel addContact(ContactModel contact) {
        if(contact.getOwner().equals((contact.getUsername()))){
            return null;
        }
        ContactModel existing = contactRepository.findByOwnerAndUsername(contact.getOwner(), contact.getUsername());
        if (existing != null) {
            return null;
        }
        ContactModel saved = contactRepository.save(contact);

        // implements symmetric relation
        ContactModel reverse = contactRepository.findByOwnerAndUsername(contact.getUsername(), contact.getOwner());
        if (reverse == null) {
            ContactModel reverseContact = new ContactModel();
            reverseContact.setOwner(contact.getUsername());
            reverseContact.setUsername(contact.getOwner());

            contactRepository.save(reverseContact);
        }
        return saved;
    }

    public List<ContactModel> getContacts(String owner) {
        return contactRepository.findByOwner(owner);
    }

}