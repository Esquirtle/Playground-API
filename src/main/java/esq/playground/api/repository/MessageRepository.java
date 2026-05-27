package esq.playground.api.repository;

import esq.playground.api.model.MessageEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<MessageEntity> messageRowMapper = (rs, rowNum) -> new MessageEntity(
            rs.getString("message_id"),
            rs.getString("conversation_id"),
            rs.getString("sender_number"),
            rs.getString("recipient_number"),
            rs.getString("body"),
            rs.getLong("sent_at"),
            rs.getInt("is_sender_copy") == 1,
            rs.getLong("created_at")
    );

    public MessageRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public MessageEntity save(MessageEntity message) {
        jdbcTemplate.update("INSERT INTO messages(message_id, conversation_id, sender_number, recipient_number, body, sent_at, is_sender_copy, created_at) VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                message.messageId(),
                message.conversationId(),
                message.senderNumber(),
                message.recipientNumber(),
                message.body(),
                message.sentAt(),
                message.isSenderCopy() ? 1 : 0,
                message.createdAt());
        return message;
    }

    public List<MessageEntity> findByConversationId(String conversationId, int limit, int offset) {
        return jdbcTemplate.query("SELECT message_id, conversation_id, sender_number, recipient_number, body, sent_at, is_sender_copy, created_at FROM messages WHERE conversation_id = ? ORDER BY sent_at ASC LIMIT ? OFFSET ?", messageRowMapper, conversationId, limit, offset);
    }
}
