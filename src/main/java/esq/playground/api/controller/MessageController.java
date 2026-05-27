package esq.playground.api.controller;

import esq.playground.api.dto.MessageRequest;
import esq.playground.api.dto.MessageResponse;
import esq.playground.api.model.MessageEntity;
import esq.playground.api.service.MessageService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<MessageResponse> saveMessage(@Valid @RequestBody MessageRequest request) {
        LOGGER.info("POST /api/messages sender={} recipient={} sentAt={}", request.getSenderNumber(), request.getRecipientNumber(), request.getSentAt());
        MessageEntity saved = messageService.saveMessage(request.getSenderNumber(), request.getRecipientNumber(), request.getBody(), request.getSentAt());
        LOGGER.info("Message created id={} conversationId={}", saved.messageId(), saved.conversationId());
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
