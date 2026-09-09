package com.example.backend.controller;

import com.example.backend.model.ContactModel;
import com.example.backend.service.ContactService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://chatloop-web.netlify.app")
public class ContactController {
    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<?> addContact(@RequestBody ContactModel contact) {
        ContactModel saved = contactService.addContact(contact);
        if (saved == null) {
            return ResponseEntity.badRequest().body("Contact already exists");
        }
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{owner}")
    public List<ContactModel> getContacts(@PathVariable String owner) {
        return contactService.getContacts(owner);
    }
}
