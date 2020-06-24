package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;

public class MealTestData {
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final Meal MEAL1 = new Meal(
            1, LocalDateTime.parse("2020-06-22 08:57:00", formatter), "Breakfast", 400);

    public static final Meal MEAL2 = new Meal(
            2, LocalDateTime.parse("2020-06-24 12:31:00", formatter), "Lunch", 400);

    public static final Meal MEAL3 = new Meal(
            3, LocalDateTime.parse("2020-06-26 15:41:00", formatter), "Dinner", 500);

    public static final Meal MEAL4 = new Meal(
            4, LocalDateTime.parse("2020-06-23 15:41:00", formatter), "Dinner", 700);

    public static final Meal MEAL5 = new Meal(
            5, LocalDateTime.parse("2020-06-23 21:41:00", formatter), "Late dinner", 1500);


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(List<Meal> actual, List<Meal> expected) {
        expected.sort(Comparator.comparing(Meal::getDateTime).reversed());
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertThat(actual).isEqualTo(Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
