package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepository implements MealRepository {

    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertMeal;


    public JdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertMeal = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("user_id", userId)
                .addValue("datetime", meal.getDateTime())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories());
        if (meal.isNew()) {
            Number newKey = insertMeal.executeAndReturnKey(map);
            meal.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE meals SET datetime=:datetime, description=:description, calories=:calories " +
                        "WHERE id=:id", map) == 0) {
            return null;
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return namedParameterJdbcTemplate.update("DELETE FROM meals WHERE id=:id AND user_id=:userId",
                new MapSqlParameterSource("id", id)
                        .addValue("userId", userId)
        ) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return namedParameterJdbcTemplate.queryForObject(
                "SELECT * FROM meals WHERE id=:id AND user_id=:userId",
                new MapSqlParameterSource("id", id)
                        .addValue("userId", userId),
                ROW_MAPPER);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return namedParameterJdbcTemplate.query(
                "SELECT * FROM meals WHERE user_id=:userId ORDER BY datetime DESC",
                new MapSqlParameterSource("userId", userId),
                ROW_MAPPER);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDate, LocalDateTime endDate, int userId) {

        return namedParameterJdbcTemplate.query(
                "SELECT * FROM meals WHERE datetime>=:startDate AND " +
                        "datetime<:endDate ORDER BY datetime",
                new MapSqlParameterSource("startDate", startDate)
                        .addValue("endDate", endDate),
                ROW_MAPPER);
    }
}
