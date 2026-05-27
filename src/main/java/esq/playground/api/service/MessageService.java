package esq.playground.api.service;

import esq.playground.api.model.ConversationSummary;
import esq.playground.api.model.MessageEntity;
import esq.playground.api.repository.ConversationRepository;
import esq.playground.api.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;

@Service
public class MessageService {

    private final PhoneService phoneService;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    public MessageService(PhoneService phoneService, ConversationRepository conversationRepository, MessageRepository messageRepository) {
        this.phoneService = phoneService;
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
    }

    @Transactional
    public MessageEntity saveMessage(String senderNumber, String recipientNumber, String body, long sentAt) {
        if (!StringUtils.hasText(senderNumber) || !StringUtils.hasText(recipientNumber)) {
            throw new IllegalArgumentException("senderNumber and recipientNumber are required");
        }
        if (!StringUtils.hasText(body)) {
            throw new IllegalArgumentException("body is required");
        }

        senderNumber = senderNumber.trim();
        recipientNumber = recipientNumber.trim();
        boolean senderIsPhone = phoneService.exists(senderNumber);
        boolean recipientIsPhone = phoneService.exists(recipientNumber);

        String phoneNumber;
        String contactNumber;
        boolean isSenderCopy;
        if (senderIsPhone) {
            phoneNumber = senderNumber;
            contactNumber = recipientNumber;
            isSenderCopy = true;
        } else if (recipientIsPhone) {
            phoneNumber = recipientNumber;
            contactNumber = senderNumber;
            isSenderCopy = false;
        } else {
            throw new IllegalArgumentException("Neither senderNumber nor recipientNumber is a registered phone");
        }

        Optional<ConversationSummary> existingConversation = conversationRepository.findByPhoneAndContact(phoneNumber, contactNumber);
        String conversationId = existingConversation.map(ConversationSummary::conversationId).orElseGet(() -> UUID.randomUUID().toString());
        int unreadCount = existingConversation.map(ConversationSummary::unreadCount).orElse(0);
        if (!isSenderCopy) {
            unreadCount++;
        }
        conversationRepository.saveOrUpdateSummary(conversationId, phoneNumber, contactNumber, sentAt, body, unreadCount);

        MessageEntity message = new MessageEntity(UUID.randomUUID().toString(), conversationId, senderNumber, recipientNumber, body, sentAt, isSenderCopy, System.currentTimeMillis());
        return messageRepository.save(message);
    }
}
