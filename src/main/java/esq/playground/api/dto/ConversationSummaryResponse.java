package esq.playground.api.dto;

public class ConversationSummaryResponse {

    private String conversationId;
    private String phoneNumber;
    private String contactNumber;
    private String displayName;
    private String lastMessageBody;
    private long lastMessageAt;
    private int unreadCount;
    private long updatedAt;

    public ConversationSummaryResponse() {
    }

    public ConversationSummaryResponse(String conversationId, String phoneNumber, String contactNumber, String displayName, String lastMessageBody, long lastMessageAt, int unreadCount, long updatedAt) {
        this.conversationId = conversationId;
        this.phoneNumber = phoneNumber;
        this.contactNumber = contactNumber;
        this.displayName = displayName;
        this.lastMessageBody = lastMessageBody;
        this.lastMessageAt = lastMessageAt;
        this.unreadCount = unreadCount;
        this.updatedAt = updatedAt;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLastMessageBody() {
        return lastMessageBody;
    }

    public void setLastMessageBody(String lastMessageBody) {
        this.lastMessageBody = lastMessageBody;
    }

    public long getLastMessageAt() {
        return lastMessageAt;
    }

    public void setLastMessageAt(long lastMessageAt) {
        this.lastMessageAt = lastMessageAt;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
