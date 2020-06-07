package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.services.map.MealMapService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        List<Meal> preMeals = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        MealMapService mealMapService = MealMapService.getMealMapService();

        for (Meal meal: preMeals) {
            mealMapService.save(meal);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        MealMapService mealMapService = MealMapService.getMealMapService();

        List<Meal> meals = new ArrayList<>();
        meals.addAll(mealMapService.findAll());

        List<MealTo> mealTos = MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, 2000);

        request.setAttribute("meals", mealTos);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MealMapService mealMapService = MealMapService.getMealMapService();

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if ("Delete".equals(action)) {
            mealMapService.deleteById((long) Integer.parseInt(request.getParameter("id")));

            List<Meal> meals = new ArrayList<>();
            meals.addAll(mealMapService.findAll());
            List<MealTo> mealTos = MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, 2000);
            LocalDateTime now = LocalDateTime.now();

            request.setAttribute("now", now);
            request.setAttribute("meals", mealTos);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }

        if ("Edit".equals(action)) {
            List<Meal> meals = new ArrayList<>();
            meals.addAll(mealMapService.findAll());

            List<MealTo> mealTos = MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, 2000);
            Meal mealToEdit = mealMapService.findById((long) Integer.parseInt(request.getParameter("id")));
            request.setAttribute("meals", mealTos);
            request.setAttribute("mealToEdit", mealToEdit);
            request.getRequestDispatcher("/mealsEdit.jsp").forward(request, response);
        }

        if ("Update".equals(action)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), formatter);
            Meal meal = new Meal(dateTime, request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
            meal.setId((long) Integer.parseInt(request.getParameter("id")));
            mealMapService.save(meal);

            List<Meal> meals = new ArrayList<>();
            meals.addAll(mealMapService.findAll());
            List<MealTo> mealTos = MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, 2000);
            LocalDateTime now = LocalDateTime.now();

            request.setAttribute("now", now);
            request.setAttribute("meals", mealTos);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);

        }

        if ("Add".equals(action)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), formatter);
            Meal meal = new Meal(dateTime, request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
            mealMapService.save(meal);

            List<Meal> meals = new ArrayList<>();
            meals.addAll(mealMapService.findAll());
            List<MealTo> mealTos = MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, 2000);
            LocalDateTime now = LocalDateTime.now();

            request.setAttribute("now", now);
            request.setAttribute("meals", mealTos);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);

        }

    }
}