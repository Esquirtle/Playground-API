package esq.playground.api.controller;

import esq.playground.api.dto.MessageRequest;
import esq.playground.api.dto.MessageResponse;
import esq.playground.api.model.MessageEntity;
import esq.playground.api.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<MessageResponse> saveMessage(@Valid @RequestBody MessageRequest request) {
        MessageEntity saved = messageService.saveMessage(request.getSenderNumber(), request.getRecipientNumber(), request.getBody(), request.getSentAt());
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(
                saved.messageId(),
                saved.conversationId(),
                saved.senderNumber(),
                saved.recipientNumber(),
                saved.body(),
                saved.sentAt(),
                saved.isSenderCopy(),
                saved.createdAt()
        ));
    }
}
