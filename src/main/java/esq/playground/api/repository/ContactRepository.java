package esq.playground.api.repository;

import esq.playground.api.model.Contact;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ContactRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Contact> contactRowMapper = (rs, rowNum) -> new Contact(
            rs.getLong("id"),
            rs.getString("phone_number"),
            rs.getString("contact_number"),
            rs.getString("display_name"),
            rs.getLong("created_at")
    );

    public ContactRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Contact> findContactsForPhone(String phoneNumber) {
        return jdbcTemplate.query("SELECT id, phone_number, contact_number, display_name, created_at FROM contacts WHERE phone_number = ? ORDER BY display_name COLLATE NOCASE, contact_number", contactRowMapper, phoneNumber);
    }

    public Optional<Contact> findByPhoneAndContactNumber(String phoneNumber, String contactNumber) {
        var rows = jdbcTemplate.query("SELECT id, phone_number, contact_number, display_name, created_at FROM contacts WHERE phone_number = ? AND contact_number = ?", contactRowMapper, phoneNumber, contactNumber);
        return rows.stream().findFirst();
    }

    public Contact saveOrUpdate(String phoneNumber, String contactNumber, String displayName) {
        long now = System.currentTimeMillis();
        jdbcTemplate.update("INSERT INTO contacts(phone_number, contact_number, display_name, created_at) VALUES(?, ?, ?, ?) ON CONFLICT(phone_number, contact_number) DO UPDATE SET display_name = excluded.display_name", phoneNumber, contactNumber, displayName, now);
        return findByPhoneAndContactNumber(phoneNumber, contactNumber).orElseThrow();
    }
}
