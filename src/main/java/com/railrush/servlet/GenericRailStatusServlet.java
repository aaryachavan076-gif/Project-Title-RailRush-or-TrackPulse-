package com.railrush.servlet;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Experiment 04: Demonstration of GenericServlet implementation.
 * GenericServlet provides a protocol-independent servlet lifecycle by implementing
 * the Servlet and ServletConfig interfaces, requiring only service() to be overridden.
 */
@WebServlet("/GenericRailStatusServlet")
public class GenericRailStatusServlet extends GenericServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();

        String clientIp = req.getRemoteAddr();
        String serverName = req.getServerName();
        int serverPort = req.getServerPort();
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>RailRush - GenericServlet Telemetry</title>");
        out.println("<style>");
        out.println("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #0b1329; color: #f8fafc; padding: 2rem; }");
        out.println(".card { max-width: 650px; margin: 0 auto; background: #162447; padding: 2rem; border-radius: 12px; box-shadow: 0 8px 24px rgba(0,0,0,0.4); border-left: 5px solid #ff6f00; }");
        out.println("h2 { color: #38bdf8; margin-top: 0; }");
        out.println(".badge { background: #ff6f00; color: #fff; padding: 4px 10px; border-radius: 4px; font-size: 0.85rem; font-weight: bold; }");
        out.println("table { width: 100%; border-collapse: collapse; margin-top: 1rem; }");
        out.println("td, th { padding: 10px; text-align: left; border-bottom: 1px solid #1f305e; }");
        out.println("th { color: #94a3b8; }");
        out.println(".btn { display: inline-block; margin-top: 1.5rem; background: #38bdf8; color: #0b1329; padding: 8px 16px; text-decoration: none; border-radius: 6px; font-weight: bold; }");
        out.println("</style></head><body>");

        out.println("<div class='card'>");
        out.println("<h2>🚆 RailRush Protocol Gateway</h2>");
        out.println("<p><span class='badge'>Experiment 04: GenericServlet</span></p>");
        out.println("<p>This response was processed by overriding <code>GenericServlet.service(ServletRequest, ServletResponse)</code>, demonstrating protocol-independent servlet handling in contrast to <code>HttpServlet</code>.</p>");
        out.println("<table>");
        out.println("<tr><th>Parameter</th><th>Value</th></tr>");
        out.println("<tr><td>Servlet Engine:</td><td>" + getServletInfo() + "</td></tr>");
        out.println("<tr><td>Server Host:</td><td>" + serverName + ":" + serverPort + "</td></tr>");
        out.println("<tr><td>Client Remote IP:</td><td>" + clientIp + "</td></tr>");
        out.println("<tr><td>Current Server Timestamp:</td><td>" + currentTime + "</td></tr>");
        out.println("<tr><td>Operational Status:</td><td><span style='color:#4ade80;'>ONLINE / READY</span></td></tr>");
        out.println("</table>");
        out.println("<a href='index.jsp' class='btn'>&larr; Return to Home</a>");
        out.println("<a href='experiments.jsp' class='btn' style='background:#ff6f00; color:#fff; margin-left: 10px;'>Lab Suite &rarr;</a>");
        out.println("</div>");
        out.println("</body></html>");
    }

    @Override
    public String getServletInfo() {
        return "RailRush Generic Rail Status Dispatcher v1.0 (Jakarta EE 10)";
    }
}
