package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/initDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private MealRepository repository;

    @Test
    public void get() {
        Meal meal = service.get(1, 100000);
        assertMatch(meal, MEAL1);
    }

    @Test
    public void delete() {
        service.delete(1, 100000);
        List<Meal> meals = service.getAll(100000);
        assertMatch(meals, Arrays.asList(MEAL2, MEAL3));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> meals = service.getBetweenInclusive(LocalDate.parse("2020-06-23"),
                LocalDate.parse("2020-06-25"), 100000);
        assertMatch(meals, Arrays.asList(MEAL2));
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(100000);
        assertMatch(meals, Arrays.asList(MEAL1, MEAL2, MEAL3));
    }

    @Test
    public void update() {
        Meal meal = MEAL1;
        meal.setCalories(700);
        service.update(meal, 100000);
        assertMatch(service.get(1, 100000), meal);
    }

    @Test
    public void create() {
        Meal meal = new Meal(
                LocalDateTime.parse("2020-06-28 12:31:00", formatter), "Lunch", 1400);
        service.create(meal, 100000);
        meal.setId(6);
        assertMatch(service.get(6, 100000), meal);
    }
}