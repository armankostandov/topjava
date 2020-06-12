package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(SecurityUtil.authUserId());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id) {
        if (repository.get(id).getId().equals(id))
            return repository.remove(id) != null;
        else
            return false;
    }

    @Override
    public Meal get(int id) {
        if (repository.get(id).getId().equals(id))
            return repository.get(id);
        else
            return null;
    }

    @Override
    public Collection<Meal> getAll() {
        List<Meal> meals = new ArrayList<>();
        for (Map.Entry<Integer, Meal> entry: repository.entrySet()) {
            if (entry.getValue().getUserId().equals(SecurityUtil.authUserId()))
                meals.add(entry.getValue());
        }
        Collections.sort(meals);
        return meals;
    }
}

