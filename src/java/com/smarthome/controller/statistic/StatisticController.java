package com.smarthome.controller.statistic;

import com.smarthome.dao.StatisticDAO;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;

public class StatisticController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        StatisticDAO dao = new StatisticDAO();

        request.setAttribute("eventStats", dao.getEventDistribution());
        request.setAttribute("lightStats", dao.getLightStatusPercentage());
        request.setAttribute("alertStats", dao.getAlertSeverityPercentage());

        // mở đúng section statistics trong admin.jsp
        request.setAttribute("CURRENT_SECTION", "statistic_section");

        request.getRequestDispatcher("statistic/statistic.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
