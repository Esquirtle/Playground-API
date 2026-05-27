package esq.playground.api.repository;

import esq.playground.api.model.Phone;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PhoneRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Phone> phoneRowMapper = (rs, rowNum) -> new Phone(
            rs.getString("phone_number"),
            rs.getString("owner_id"),
            rs.getLong("created_at")
    );

    public PhoneRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Phone> findByPhoneNumber(String phoneNumber) {
        var rows = jdbcTemplate.query("SELECT phone_number, owner_id, created_at FROM phones WHERE phone_number = ?", phoneRowMapper, phoneNumber);
        return rows.stream().findFirst();
    }

    public Phone save(String phoneNumber, String ownerId) {
        long now = System.currentTimeMillis();
        jdbcTemplate.update("INSERT OR IGNORE INTO phones(phone_number, owner_id, created_at) VALUES(?, ?, ?)", phoneNumber, ownerId, now);
        return new Phone(phoneNumber, ownerId, now);
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM phones WHERE phone_number = ?", Integer.class, phoneNumber);
        return count != null && count > 0;
    }
}
