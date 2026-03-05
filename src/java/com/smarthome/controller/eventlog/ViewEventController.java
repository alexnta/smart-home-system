package com.smarthome.controller.eventlog;

import com.smarthome.dao.EventLogDAO;
import com.smarthome.dto.EventLogDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ViewEventServlet", urlPatterns = {"/ViewEventController"})
public class ViewEventController extends HttpServlet {

    private static final String EVENT_LIST_PAGE = "eventList.jsp";
    private static final String ERROR_PAGE = "error.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;

        try {
            EventLogDAO dao = new EventLogDAO();
            List<EventLogDTO> eventList = dao.getAllEvents();

            request.setAttribute("EVENT_LIST", eventList);
            url = EVENT_LIST_PAGE;

        } catch (Exception e) {
            log("Error at ViewEventController: " + e.toString());
            request.setAttribute("ERROR", "Cannot load event log list!");
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
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