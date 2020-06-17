package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<Meal> getAll() {
        log.info("getAll");
        return service.getAll(SecurityUtil.authUserId());
    }

    public List<Meal> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("getAll");
        return service.getFiltered(startDate, endDate, startTime, endTime);
    }

    public Meal get(int id) {
        log.info("get {}", id);
        if (service.get(id).getUserId() == SecurityUtil.authUserId())
            return service.get(id);
        else
            throw new NotFoundException(service.get(id) + " has user id " + service.get(id).getUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        meal.setUserId(SecurityUtil.authUserId());
        return service.create(meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        if (service.get(id).getUserId() == SecurityUtil.authUserId())
            service.delete(id);
        else
            throw new NotFoundException(service.get(id) + " has user id " + service.get(id).getUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        if (meal.getUserId() == SecurityUtil.authUserId())
            service.update(meal);
        else
            throw new NotFoundException(meal + " has user id " + meal.getUserId());
    }

}