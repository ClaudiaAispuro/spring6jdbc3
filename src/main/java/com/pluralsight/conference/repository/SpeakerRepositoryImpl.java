package com.pluralsight.conference.repository;

import com.pluralsight.conference.mapper.SpeakerRowMapper;
import com.pluralsight.conference.model.Speaker;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

@Repository("speakerRepository")
public class SpeakerRepositoryImpl implements SpeakerRepository {

    private JdbcTemplate jdbcTemplate;

    public SpeakerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Speaker> findAll() {
        String sql = "SELECT * FROM speaker";
        return jdbcTemplate.query(sql, new SpeakerRowMapper());
    }

    @Override
    public Speaker create(Speaker speaker) {
        String sql = "INSERT INTO speaker (name) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, speaker.getName());
            return ps;
        }, keyHolder);

        return get(keyHolder.getKey().intValue());
    }

    @Override
    public Speaker get(int id) throws NoSuchElementException {
        String sql = "SELECT id, name FROM speaker WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new SpeakerRowMapper(), id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException("No se encontro speaker con ID: " + id);
        }
    }
}
