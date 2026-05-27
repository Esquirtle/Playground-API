package esq.playground.api.dto;

public class MessageResponse {

    private String messageId;
    private String conversationId;
    private String senderNumber;
    private String recipientNumber;
    private String body;
    private long sentAt;
    private boolean senderCopy;
    private long createdAt;

    public MessageResponse() {
    }

    public MessageResponse(String messageId, String conversationId, String senderNumber, String recipientNumber, String body, long sentAt, boolean senderCopy, long createdAt) {
        this.messageId = messageId;
        this.conversationId = conversationId;
        this.senderNumber = senderNumber;
        this.recipientNumber = recipientNumber;
        this.body = body;
        this.sentAt = sentAt;
        this.senderCopy = senderCopy;
        this.createdAt = createdAt;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getSenderNumber() {
        return senderNumber;
    }

    public void setSenderNumber(String senderNumber) {
        this.senderNumber = senderNumber;
    }

    public String getRecipientNumber() {
        return recipientNumber;
    }

    public void setRecipientNumber(String recipientNumber) {
        this.recipientNumber = recipientNumber;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getSentAt() {
        return sentAt;
    }

    public void setSentAt(long sentAt) {
        this.sentAt = sentAt;
    }

    public boolean isSenderCopy() {
        return senderCopy;
    }

    public void setSenderCopy(boolean senderCopy) {
        this.senderCopy = senderCopy;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
