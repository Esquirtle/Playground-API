package esq.playground.api.service;

import esq.playground.api.exception.NotFoundException;
import esq.playground.api.model.ConversationSummary;
import esq.playground.api.model.MessageEntity;
import esq.playground.api.repository.ConversationRepository;
import esq.playground.api.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    public ConversationService(ConversationRepository conversationRepository, MessageRepository messageRepository) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
    }

    public List<ConversationSummary> listConversations(String phoneNumber, int limit, int offset) {
        return conversationRepository.findConversationsForPhone(phoneNumber.trim(), limit, offset);
    }

    public ConversationSummary getConversation(String phoneNumber, String contactNumber) {
        return conversationRepository.findByPhoneAndContact(phoneNumber.trim(), contactNumber.trim())
                .orElseThrow(() -> new NotFoundException("Conversation not found for " + phoneNumber + " and " + contactNumber));
    }

    public List<MessageEntity> getConversationHistory(String phoneNumber, String contactNumber, int limit, int offset) {
        ConversationSummary conversation = getConversation(phoneNumber, contactNumber);
        return messageRepository.findByConversationId(conversation.conversationId(), limit, offset);
    }
}
