package com.example.pgdemo.repository;

import com.example.pgdemo.model.Event;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Repository
public class EventRepository {
  private final JdbcTemplate jdbcTemplate;

  private final RowMapper<Event> rowMapper = (rs, rowNum) ->
          Event.builder()
                  .id(rs.getLong("id"))
                  .title(rs.getString("title"))
                  .description(rs.getString("description"))
                  .createdAt(rs.getObject("created_at", OffsetDateTime.class))
                  .updatedAt(rs.getObject("updated_at", OffsetDateTime.class))
                  .build();

  public EventRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public Event save(Event event) {
    if (event.getId() == null) {
      return insert(event);
    }
    return update(event);
  }

  private Event insert(Event event) {
    String sql = "INSERT INTO events (title, description) VALUES (?, ?) RETURNING id";
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});  // Specify the column
      ps.setString(1, event.getTitle());
      ps.setString(2, event.getDescription());
      return ps;
    }, keyHolder);

    // Extract just the id from the KeyHolder
    Number key = keyHolder.getKey();
    if (key == null) {
      throw new RuntimeException("Failed to retrieve generated key");
    }

    return findById(key.longValue()).orElseThrow();
  }

  private Event update(Event event) {
    String sql = "UPDATE events SET title = ?, description = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
    int updated = jdbcTemplate.update(sql,
            event.getTitle(),
            event.getDescription(),
            event.getId()
    );

    if (updated == 0) {
      throw new RuntimeException("Event not found with id: " + event.getId());
    }

    return findById(event.getId()).orElseThrow();
  }

  public Optional<Event> findById(Long id) {
    String sql = "SELECT * FROM events WHERE id = ?";
    List<Event> events = jdbcTemplate.query(sql, rowMapper, id);
    return events.isEmpty() ? Optional.empty() : Optional.of(events.get(0));
  }

  public List<Event> findAll() {
    return jdbcTemplate.query("SELECT * FROM events ORDER BY created_at DESC", rowMapper);
  }

  public void deleteById(Long id) {
    String sql = "DELETE FROM events WHERE id = ?";
    int deleted = jdbcTemplate.update(sql, id);
    if (deleted == 0) {
      throw new RuntimeException("Event not found with id: " + id);
    }
  }
}
