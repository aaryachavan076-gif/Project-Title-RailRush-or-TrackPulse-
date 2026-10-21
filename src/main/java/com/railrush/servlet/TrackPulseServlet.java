package com.railrush.servlet;

import com.railrush.dao.DBConnection;
import com.railrush.model.LiveStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * TrackPulseServlet providing live telemetry feed and train delay tracking (Exp 04).
 */
@WebServlet("/TrackPulseServlet")
public class TrackPulseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<LiveStatus> telemetryList = new ArrayList<>();
        String trainNumberFilter = request.getParameter("trainNumber");

        String sql = "SELECT lt.*, t.train_name FROM live_tracking lt " +
                     "JOIN trains t ON lt.train_number = t.train_number";
        if (trainNumberFilter != null && !trainNumberFilter.trim().isEmpty()) {
            sql += " WHERE lt.train_number = ?";
        }
        sql += " ORDER BY lt.status_updated_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (trainNumberFilter != null && !trainNumberFilter.trim().isEmpty()) {
                ps.setString(1, trainNumberFilter.trim());
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LiveStatus status = new LiveStatus();
                    status.setTrackingId(rs.getInt("tracking_id"));
                    status.setTrainNumber(rs.getString("train_number"));
                    status.setTrainName(rs.getString("train_name"));
                    status.setCurrentStation(rs.getString("current_station"));
                    status.setNextStation(rs.getString("next_station"));
                    status.setDelayMinutes(rs.getInt("delay_minutes"));
                    if (rs.getTimestamp("status_updated_at") != null) {
                        status.setStatusUpdatedAt(rs.getTimestamp("status_updated_at").toLocalDateTime());
                    }
                    telemetryList.add(status);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading telemetry: " + e.getMessage());
        }

        request.setAttribute("telemetryList", telemetryList);
        request.getRequestDispatcher("track_pulse.jsp").forward(request, response);
    }
}
