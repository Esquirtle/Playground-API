package esq.playground.api.controller;

import esq.playground.api.dto.ConversationSummaryResponse;
import esq.playground.api.dto.MessageResponse;
import esq.playground.api.model.ConversationSummary;
import esq.playground.api.model.Contact;
import esq.playground.api.model.MessageEntity;
import esq.playground.api.service.ConversationService;
import esq.playground.api.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    private final ConversationService conversationService;
    private final ContactService contactService;

    public ConversationController(ConversationService conversationService, ContactService contactService) {
        this.conversationService = conversationService;
        this.contactService = contactService;
    }

    @GetMapping("/{phoneNumber}")
    public ResponseEntity<List<ConversationSummaryResponse>> listConversations(
            @PathVariable String phoneNumber,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        List<ConversationSummaryResponse> responses = conversationService.listConversations(phoneNumber, limit, offset).stream()
                .map(summary -> mapToResponse(summary, contactService.findContact(summary.phoneNumber(), summary.contactNumber())))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{phoneNumber}/{contactNumber}")
    public ResponseEntity<List<MessageResponse>> getConversationHistory(@PathVariable String phoneNumber,
                                                                        @PathVariable String contactNumber,
                                                                        @RequestParam(defaultValue = "100") int limit,
                                                                        @RequestParam(defaultValue = "0") int offset) {
        List<MessageEntity> history = conversationService.getConversationHistory(phoneNumber, contactNumber, limit, offset);
        List<MessageResponse> responses = history.stream()
                .map(message -> new MessageResponse(
                        message.messageId(),
                        message.conversationId(),
                        message.senderNumber(),
                        message.recipientNumber(),
                        message.body(),
                        message.sentAt(),
                        message.isSenderCopy(),
                        message.createdAt()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    private ConversationSummaryResponse mapToResponse(ConversationSummary summary, java.util.Optional<Contact> contact) {
        return new ConversationSummaryResponse(
                summary.conversationId(),
                summary.phoneNumber(),
                summary.contactNumber(),
                contact.map(Contact::displayName).orElse(null),
                summary.lastMessageBody(),
                summary.lastMessageAt(),
                summary.unreadCount(),
                summary.updatedAt()
        );
    }
}
