package esq.playground.api.controller;

import esq.playground.api.dto.ContactRequest;
import esq.playground.api.model.Contact;
import esq.playground.api.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/{phoneNumber}")
    public ResponseEntity<List<Contact>> getContacts(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(contactService.getContacts(phoneNumber));
    }

    @PostMapping("/{phoneNumber}")
    public ResponseEntity<Contact> createOrUpdateContact(@PathVariable String phoneNumber, @Valid @RequestBody ContactRequest request) {
        Contact saved = contactService.createOrUpdateContact(phoneNumber, request.getContactNumber(), request.getDisplayName());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
