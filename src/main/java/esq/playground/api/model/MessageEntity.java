package esq.playground.api.model;

public record MessageEntity(String messageId, String conversationId, String senderNumber, String recipientNumber, String body, long sentAt, boolean isSenderCopy, long createdAt) {
}
