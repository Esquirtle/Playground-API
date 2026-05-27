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

        if (!senderIsPhone && !recipientIsPhone) {
            throw new IllegalArgumentException("Neither senderNumber nor recipientNumber is a registered phone");
        }

        MessageEntity lastSavedMessage = null;

        if (senderIsPhone) {
            String senderConversationId = upsertConversationSummary(senderNumber, recipientNumber, sentAt, body, 0);
            MessageEntity senderCopy = new MessageEntity(UUID.randomUUID().toString(), senderConversationId, senderNumber, recipientNumber, body, sentAt, true, System.currentTimeMillis());
            lastSavedMessage = messageRepository.save(senderCopy);
        }

        if (recipientIsPhone) {
            String recipientConversationId = upsertConversationSummary(recipientNumber, senderNumber, sentAt, body, senderIsPhone ? 1 : 0);
            MessageEntity recipientCopy = new MessageEntity(UUID.randomUUID().toString(), recipientConversationId, senderNumber, recipientNumber, body, sentAt, false, System.currentTimeMillis());
            lastSavedMessage = messageRepository.save(recipientCopy);
        }

        return lastSavedMessage;
    }

    private String upsertConversationSummary(String phoneNumber, String contactNumber, long sentAt, String body, int unreadDelta) {
        var existingConversation = conversationRepository.findByPhoneAndContact(phoneNumber, contactNumber);
        String conversationId = existingConversation.map(ConversationSummary::conversationId).orElseGet(() -> UUID.randomUUID().toString());
        int unreadCount = existingConversation.map(ConversationSummary::unreadCount).orElse(0) + unreadDelta;
        conversationRepository.saveOrUpdateSummary(conversationId, phoneNumber, contactNumber, sentAt, body, unreadCount);
        return conversationId;
    }
}
