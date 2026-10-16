package com.railrush.servlet;

import com.railrush.dao.TrainDAO;
import com.railrush.model.Train;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * SearchTrainServlet processing queries for trains between stations (Exp 04).
 */
@WebServlet("/SearchTrainServlet")
public class SearchTrainServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final TrainDAO trainDAO = new TrainDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String source = request.getParameter("source");
        String destination = request.getParameter("destination");
        String date = request.getParameter("date");

        try {
            List<Train> trains;
            if (source != null && destination != null && !source.trim().isEmpty() && !destination.trim().isEmpty()) {
                trains = trainDAO.search(source.trim(), destination.trim());
            } else {
                trains = trainDAO.findAll();
            }
            request.setAttribute("trains", trains);
            request.setAttribute("searchSource", source);
            request.setAttribute("searchDestination", destination);
            request.setAttribute("journeyDate", date);
            request.getRequestDispatcher("search_results.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error searching trains: " + e.getMessage());
            request.getRequestDispatcher("search_results.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
