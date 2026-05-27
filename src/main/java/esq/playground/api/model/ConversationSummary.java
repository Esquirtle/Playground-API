package esq.playground.api.model;

public record ConversationSummary(String conversationId, String phoneNumber, String contactNumber, String lastMessageBody, long lastMessageAt, int unreadCount, long updatedAt) {
}
