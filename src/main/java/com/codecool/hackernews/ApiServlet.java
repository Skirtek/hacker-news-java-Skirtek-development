package com.codecool.hackernews;

import com.codecool.hackernews.dto.Endpoints;
import com.codecool.hackernews.integration.HackerApi;
import com.codecool.hackernews.integration.HackerApiImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.util.Optional;

@WebServlet(name = "apiServlet", value = "/api")
public class ApiServlet extends HttpServlet {
    private final HackerApi hackerApi = new HackerApiImpl();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        // http://localhost:8080/hackernews_war_exploded/api?category=news&page=1
        String category = req.getParameter("category");

        if (category == null || category.trim().length() == 0) {
            resp.sendError(400, "Category not provided");
            return;
        }

        Optional<Endpoints> endpoint = Endpoints.getEndpointByName(category);

        if (endpoint.isEmpty()) {
            resp.sendError(404, "Category not found");
            return;
        }

        String page = req.getParameter("page");

        if (page == null || page.trim().length() == 0) {
            resp.sendError(400, "Page not provided");
            return;
        }

        int pageNumber;

        try {
            pageNumber = Integer.parseInt(page);

            if (pageNumber < 1) {
                resp.sendError(400, "Invalid page");
                return;
            }
        } catch (NumberFormatException e) {
            resp.sendError(400, "Invalid page");
            return;
        }

        String response = hackerApi.getNewsAsJson(endpoint.get(), pageNumber);
        resp.setContentType("application/json");
        resp.getWriter().println(response);
    }
}
