package esq.playground.api.repository;

import esq.playground.api.model.ConversationSummary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ConversationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<ConversationSummary> conversationRowMapper = (rs, rowNum) -> new ConversationSummary(
            rs.getString("conversation_id"),
            rs.getString("phone_number"),
            rs.getString("contact_number"),
            rs.getString("last_message_body"),
            rs.getLong("last_message_at"),
            rs.getInt("unread_count"),
            rs.getLong("updated_at")
    );

    public ConversationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<ConversationSummary> findByPhoneAndContact(String phoneNumber, String contactNumber) {
        var rows = jdbcTemplate.query("SELECT conversation_id, phone_number, contact_number, last_message_body, last_message_at, unread_count, updated_at FROM conversations WHERE phone_number = ? AND contact_number = ?", conversationRowMapper, phoneNumber, contactNumber);
        return rows.stream().findFirst();
    }

    public List<ConversationSummary> findConversationsForPhone(String phoneNumber, int limit, int offset) {
        return jdbcTemplate.query("SELECT conversation_id, phone_number, contact_number, last_message_body, last_message_at, unread_count, updated_at FROM conversations WHERE phone_number = ? ORDER BY last_message_at DESC LIMIT ? OFFSET ?", conversationRowMapper, phoneNumber, limit, offset);
    }

    public ConversationSummary saveOrUpdateSummary(String conversationId, String phoneNumber, String contactNumber, long lastMessageAt, String lastMessageBody, int unreadCount) {
        long now = System.currentTimeMillis();
        jdbcTemplate.update("INSERT INTO conversations(conversation_id, phone_number, contact_number, last_message_at, last_message_body, unread_count, updated_at) VALUES(?, ?, ?, ?, ?, ?, ?) ON CONFLICT(conversation_id) DO UPDATE SET last_message_at = excluded.last_message_at, last_message_body = excluded.last_message_body, unread_count = excluded.unread_count, updated_at = excluded.updated_at", conversationId, phoneNumber, contactNumber, lastMessageAt, lastMessageBody, unreadCount, now);
        return findByPhoneAndContact(phoneNumber, contactNumber).orElseThrow();
    }
}
