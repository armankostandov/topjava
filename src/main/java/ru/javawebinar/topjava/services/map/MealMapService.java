package ru.javawebinar.topjava.services.map;

import ru.javawebinar.topjava.model.Meal;

import java.util.Set;

public class MealMapService extends AbstractMapService<Meal, Long> {

    private static MealMapService mealMapService;

    public static synchronized MealMapService getMealMapService() {
        if (mealMapService == null) {
            mealMapService = new MealMapService();
        }
        return mealMapService;
    }

    @Override
    public Set<Meal> findAll() { return super.findAll(); }

    @Override
    public Meal findById(Long aLong) { return super.findById(aLong); }

    @Override
    public Meal save(Meal object) { return super.save(object); }

    @Override
    public void deleteById(Long aLong) { super.deleteById(aLong); }

    @Override
    public void delete(Meal object) { super.delete(object); }
}
